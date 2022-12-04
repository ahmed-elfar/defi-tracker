package com.xyvo.defi.repository;

import com.xyvo.defi.domain.transactions.Pending;
import com.xyvo.defi.dto.PendingDto;
import com.xyvo.defi.mapper.PendingMapper;
import com.xyvo.defi.repository.api.PendingRepo;
import com.xyvo.defi.utils.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class PendingInMemoryStore {

    private static final AtomicBoolean invokeOnce = new AtomicBoolean();

    private final AtomicLong trxId;
    private final Lock lock;
    private Map <Integer , Map<Long, PendingDto>> perNetworkPendingTrx;
    private Map<PendingDto, Long> crossNetworkPendingTrx;

    private final PendingRepo pendingRepo;

    @Autowired
    PendingInMemoryStore(PendingRepo pendingRepo) {
        this.trxId = new AtomicLong(0);
        lock = new ReentrantLock();
        this.perNetworkPendingTrx = createViewMap();
        this.crossNetworkPendingTrx = new HashMap<>(0, 1);//ConcurrentHashMap.newKeySet(0);
        this.pendingRepo = pendingRepo;
    }

    private Map<Integer, Map<Long, PendingDto>> createViewMap() {
        Map<Integer, Map<Long, PendingDto>> view = new HashMap<>(BlockChain.values().length, 1);
        for(BlockChain blockChain: BlockChain.values()) {
            view.put(blockChain.chainId, new HashMap(0, 1));
        }
        return view;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    void initializeData() {
        if(!invokeOnce.compareAndSet(false, true)) return;

        List<Pending> databasePendingTrx = pendingRepo.findAll();
        List<PendingDto> dtoPendingTrx = databasePendingTrx.stream()
                .map(pendingTrx -> {
                    var map = perNetworkPendingTrx.get(pendingTrx.getNetworkId());
                    var pendingTrxDto = PendingMapper.toDto(pendingTrx);
                    var key = trxId.incrementAndGet();
                    pendingTrxDto.setId(key);
                    map.put(key, pendingTrxDto);
                    return pendingTrxDto;
                }).collect(Collectors.toList());

        pendingRepo.deleteAllInBatch();

        //avoid last updated and generated id by hibernate, temp solution for embedded database.
        dtoPendingTrx.stream()
                .map(dto -> PendingMapper.toEntityWithTimeStamp(dto))
                .forEach(pendingTrxEntity -> pendingRepo.insert(
                        pendingTrxEntity.getId(),
                        pendingTrxEntity.getCreated(),
                        pendingTrxEntity.getUpdated(),
                        pendingTrxEntity.getAttempts(),
                        pendingTrxEntity.getCurrentPrice(),
                        pendingTrxEntity.getDexId(),
                        pendingTrxEntity.getExchangeEvery(),
                        pendingTrxEntity.getNetworkId(),
                        pendingTrxEntity.getStatus().ordinal(),
                        pendingTrxEntity.getTokenAddress(),
                        pendingTrxEntity.getTokenName(),
                        pendingTrxEntity.getTokenSymbol(),
                        pendingTrxEntity.getTotalLiquidity()
                ));

        dtoPendingTrx.forEach(dto ->{
            perNetworkPendingTrx.get(dto.getNetworkId()).put(dto.getId(), dto);
            crossNetworkPendingTrx.put(dto, dto.getId());
        });
    }

    public Collection<PendingDto> getAllPending() {
        return crossNetworkPendingTrx.keySet();
    }

    public PendingDto getOrCreate(PendingDto pendingDto) {
        Long id = crossNetworkPendingTrx.get(pendingDto);
        if(id != null) {
            return perNetworkPendingTrx.get(pendingDto.getNetworkId()).get(id);
        }
        return updateAll(pendingDto);
    }

    private PendingDto updateAll(PendingDto pendingDto) {
        lock.lock();
        try {
            Long id = crossNetworkPendingTrx.get(pendingDto);
            Integer networkId = pendingDto.getNetworkId();
            if(id != null) {
                return perNetworkPendingTrx.get(networkId).get(id);
            }
            PendingDto newPendingDto = createPendingDto(pendingDto);
            var newMap = new HashMap<Long, PendingDto>(perNetworkPendingTrx.get(networkId).size() + 1, 1);
            perNetworkPendingTrx.get(networkId).entrySet().forEach(entry -> newMap.put(entry.getKey(), entry.getValue()));
            var crossMap = new HashMap<PendingDto, Long>(crossNetworkPendingTrx.size() +1, 1);
            crossNetworkPendingTrx.entrySet().forEach(entry -> crossMap.put(entry.getKey(), entry.getValue()));
            //add new value
            newMap.put(newPendingDto.getId(), newPendingDto);
            crossMap.put(newPendingDto, newPendingDto.getId());
            //update view and cross maps
            perNetworkPendingTrx.put(networkId, newMap);
            crossNetworkPendingTrx = crossMap;
            return newPendingDto;
        } finally {
            lock.unlock();
        }
    }

    private PendingDto createPendingDto(PendingDto pendingDto) {
        PendingDto newPendingDto = new PendingDto(pendingDto.getNetworkId(), pendingDto.getDexId(), pendingDto.getTokenAddress(),
                pendingDto.getTokenSymbol(), pendingDto.getTokenName());
        newPendingDto.setId(trxId.incrementAndGet());
        newPendingDto.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        newPendingDto.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
        newPendingDto.setCurrentPrice(0D);
        newPendingDto.setExchangeEvery(0);
        newPendingDto.setTotalLiquidity(0D);
        newPendingDto.setAttempts(0);
        return newPendingDto;
    }
}
