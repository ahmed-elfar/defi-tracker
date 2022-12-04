package com.xyvo.defi.service;

import com.xyvo.defi.dto.PendingDto;
import com.xyvo.defi.mapper.PendingMapper;
import com.xyvo.defi.repository.PendingInMemoryStore;
import com.xyvo.defi.repository.api.PendingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PendingService {

    private PendingInMemoryStore pendingInMemoryStore;
    private PendingRepo pendingRepo;

    @Autowired
    public PendingService(PendingInMemoryStore pendingInMemoryStore, PendingRepo pendingRepo) {
        this.pendingInMemoryStore = pendingInMemoryStore;
        this.pendingRepo = pendingRepo;
    }

    public Collection<PendingDto> getPendingForAllNetworks() {
        return pendingInMemoryStore.getAllPending();
    }

    public List<PendingDto> getAllPendingForNetwork(int networkId) {
        return PendingMapper.toDto(pendingRepo.findByNetworkId(networkId));
    }

    public PendingDto getOrCreate(PendingDto pendingDto) {
        return pendingInMemoryStore.getOrCreate(pendingDto);
    }
}
