package com.xyvo.defi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xyvo.defi.domain.profile.Settings.Strategy;


@JsonInclude()
public class SettingsDto extends AuditedDto {

    private Long userId;

    private Boolean lockedLiquidityOnly;

    private Strategy buyStrategy;

    private Strategy sellStrategy;

    private Integer maxTrx;

    private Integer minLiquidity;

    private Integer autoSellPercent;

    private Double trxValue;

    private Integer slippage;

    private int orderInterval;

    public SettingsDto() {
    }

    /**
     * User_id, as an id for this entity, OneToOne Mapping
     * @return
     */
    public Long getId() {
        return userId;
    }

    /**
     * User_id, as an id for this entity, OneToOne Mapping
     * @param userId
     */
    public void setId(Long userId) {
        this.userId = userId;
    }

    public Strategy getBuyStrategy() {
        return buyStrategy;
    }

    public void setBuyStrategy(Strategy buyStrategy) {
        this.buyStrategy = buyStrategy;
    }

    public Boolean getLockedLiquidityOnly() {
        return lockedLiquidityOnly;
    }

    public void setLockedLiquidityOnly(Boolean lockedLiquidityOnly) {
        this.lockedLiquidityOnly = lockedLiquidityOnly;
    }

    public int getMaxTrx() {
        return maxTrx;
    }

    public void setMaxTrx(int maxTrx) {
        this.maxTrx = maxTrx;
    }

    public Integer getMinLiquidity() {
        return minLiquidity;
    }

    public void setMinLiquidity(Integer minLiquidity) {
        this.minLiquidity = minLiquidity;
    }

    public Double getTrxValue() {
        return trxValue;
    }

    public void setTrxValue(Double trxValue) {
        this.trxValue = trxValue;
    }

    public Integer getSlippage() {
        return slippage;
    }

    public void setSlippage(Integer slippage) {
        this.slippage = slippage;
    }

    public Strategy getSellStrategy() {
        return sellStrategy;
    }

    public void setSellStrategy(Strategy sellStrategy) {
        this.sellStrategy = sellStrategy;
    }

    public Integer getAutoSellPercent() {
        return autoSellPercent;
    }

    public void setAutoSellPercent(Integer autoSellPercent) {
        this.autoSellPercent = autoSellPercent;
    }

    public int getOrderInterval() {
        return orderInterval;
    }

    public void setOrderInterval(int orderInterval) {
        this.orderInterval = orderInterval;
    }

    public static SettingsDto getDefaultSettings() {
        SettingsDto settings = new SettingsDto();
        settings.userId = null;
        settings.lockedLiquidityOnly = true;
        settings.buyStrategy = Strategy.MANUAL;
        settings.sellStrategy = Strategy.MANUAL;
        settings.minLiquidity = 1000;
        settings.autoSellPercent = 50;
        settings.maxTrx = 100;
        settings.trxValue = 2D;
        settings.slippage = 20;
        settings.orderInterval = 60;
        return settings;
    }
}
