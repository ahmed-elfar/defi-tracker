package com.xyvo.defi.mapper;

import com.xyvo.defi.domain.transactions.Pending;
import com.xyvo.defi.dto.PendingDto;
import com.xyvo.defi.validator.PendingValidator;

import java.util.List;
import java.util.stream.Collectors;


public class PendingMapper {

    private PendingMapper(){}

    public static PendingDto toDto(Pending pending) {
        PendingDto pendingDto = new PendingDto(pending.getNetworkId(), pending.getDexId(), pending.getTokenAddress()
        , pending.getTokenSymbol(), pending.getTokenName());
        AuditedMapper.mapTimeStamp(pendingDto, pending);
        pendingDto.setId(pending.getId());
//        pendingDto.setNetworkId(pending.getNetworkId());
//        pendingDto.setDexId(pending.getDexId());
//        pendingDto.setTokenAddress(pending.getTokenAddress());
//        pendingDto.setTokenSymbol(pending.getTokenSymbol());
//        pendingDto.setTokenName(pending.getTokenName());
        pendingDto.setCurrentPrice(pending.getCurrentPrice());
        pendingDto.setTotalLiquidity(pending.getTotalLiquidity());
        pendingDto.setStatus(pending.getStatus());
        pendingDto.setExchangeEvery(pending.getExchangeEvery());
        pendingDto.setAttempts(pending.getAttempts());
        return pendingDto;
    }

    public static Pending toEntityWithTimeStamp(PendingDto pendingDto) {
        Pending pending = toEntity(pendingDto);
        pending.setCreated(pendingDto.getCreatedTimestamp());
        pending.setUpdated(pendingDto.getUpdatedTimestamp());
        return pending;
    }

    public static Pending toEntity(PendingDto pendingDto) {
        PendingValidator.validateSettings(pendingDto);
        Pending pendingTrx = new Pending();
        pendingTrx.setId(pendingDto.getId());
        pendingTrx.setNetworkId(pendingDto.getNetworkId());
        pendingTrx.setDexId(pendingDto.getDexId());
        pendingTrx.setTokenAddress(pendingDto.getTokenAddress());
        pendingTrx.setTokenSymbol(pendingDto.getTokenSymbol());
        pendingTrx.setTokenName(pendingDto.getTokenName());
        pendingTrx.setCurrentPrice(pendingDto.getCurrentPrice());
        pendingTrx.setTotalLiquidity(pendingDto.getTotalLiquidity());
        pendingTrx.setStatus(pendingDto.getStatus());
        pendingTrx.setExchangeEvery(pendingDto.getExchangeEvery());
        pendingTrx.setAttempts(pendingDto.getAttempts());
        return pendingTrx;
    }

    public static List<PendingDto> toDto(List<Pending> all) {
        return all.stream().map(PendingMapper::toDto).collect(Collectors.toList());
    }
}
