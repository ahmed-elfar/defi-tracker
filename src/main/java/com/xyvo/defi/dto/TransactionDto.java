package com.xyvo.defi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyvo.defi.domain.transactions.Transaction.Status;

@JsonInclude
public abstract class TransactionDto extends AuditedDto {

    protected Long id;

    protected final Integer networkId;

    protected final Integer dexId;

    protected final String tokenAddress;

    protected final String tokenSymbol;

    protected final String tokenName;

    protected Double currentPrice;

    protected Double totalLiquidity;

    protected Status status = Status.UNKNOWN;

    protected Integer exchangeEvery;

    protected Integer attempts;

    @JsonCreator
    protected TransactionDto(@JsonProperty Integer networkId, @JsonProperty Integer dexId,
                          @JsonProperty String tokenAddress, @JsonProperty String tokenSymbol, @JsonProperty String tokenName) {
        this.id = id;
        this.networkId = networkId;
        this.dexId = dexId;
        this.tokenAddress = tokenAddress;
        this.tokenSymbol = tokenSymbol;
        this.tokenName = tokenName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public Integer getDexId() {
        return dexId;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }
//
//    public void setNetworkId(Integer networkId) {
//        this.networkId = networkId;
//    }
//
//    public void setDexId(Integer dexId) {
//        this.dexId = dexId;
//    }
//
//    public void setTokenAddress(String tokenAddress) {
//        this.tokenAddress = tokenAddress;
//    }
//
//    public void setTokenSymbol(String tokenSymbol) {
//        this.tokenSymbol = tokenSymbol;
//    }
//
//    public void setTokenName(String tokenName) {
//        this.tokenName = tokenName;
//    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public String getTokenName() {
        return tokenName;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getTotalLiquidity() {
        return totalLiquidity;
    }

    public void setTotalLiquidity(Double totalLiquidity) {
        this.totalLiquidity = totalLiquidity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getExchangeEvery() {
        return exchangeEvery;
    }

    public void setExchangeEvery(Integer exchangeEvery) {
        this.exchangeEvery = exchangeEvery;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

}
