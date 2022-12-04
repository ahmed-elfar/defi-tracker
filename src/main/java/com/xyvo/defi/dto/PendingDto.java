package com.xyvo.defi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyvo.defi.domain.transactions.Transaction.Status;

import java.util.Objects;

@JsonInclude
public class PendingDto extends TransactionDto {

    public PendingDto (@JsonProperty Integer networkId, @JsonProperty Integer dexId,
    @JsonProperty String tokenAddress, @JsonProperty String tokenSymbol, @JsonProperty String tokenName) {
        super(networkId, dexId, tokenAddress, tokenSymbol, tokenName);
        this.status = Status.PENDING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionDto that = (TransactionDto) o;
        if (!Objects.equals(networkId, that.networkId)) return false;
        if (!Objects.equals(dexId, that.dexId)) return false;
        if (!Objects.equals(tokenAddress, that.tokenAddress)) return false;
        if (!Objects.equals(tokenSymbol, that.tokenSymbol)) return false;
        return Objects.equals(tokenName, that.tokenName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(networkId, dexId, tokenAddress, tokenSymbol, tokenName);
    }
}
