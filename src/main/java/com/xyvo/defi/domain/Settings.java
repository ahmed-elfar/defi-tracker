package com.xyvo.defi.domain;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.DOMAIN_SCHEMA;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "settings", schema = DOMAIN_SCHEMA)
public class Settings extends Audited {

    public enum Strategy {
        AUTO,
        MANUAL
    }

//    @Transient
//    private static final transient String ID_GEN = "id_gen3";
//
//    @Id
//    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column
    private Boolean lockedLiquidityOnly;

    //manual or auto
    @Column(nullable = false)
    private Strategy buyStrategy = Strategy.MANUAL;

    //manual or auto
    @Column(nullable = false)
    private Strategy sellStrategy = Strategy.MANUAL;

    @Column(nullable = false)
    private Integer maxTrx;

    @Column
    private Integer minLiquidity /*= 1000*/;

    @Column
    private Integer autoSellPercent /*= 20*/;

    @Column(nullable = false)
    private Double trxValue;

    @Column(nullable = false)
    private Integer slippage = 0;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Settings() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Strategy getSellStrategy() {
        return sellStrategy;
    }

    public void setSellStrategy(Strategy sellStrategy) {
        this.sellStrategy = sellStrategy;
    }

    public void setMaxTrx(Integer maxTrx) {
        this.maxTrx = maxTrx;
    }

    public Integer getAutoSellPercent() {
        return autoSellPercent;
    }

    public void setAutoSellPercent(Integer autoSellPercent) {
        this.autoSellPercent = autoSellPercent;
    }
}
