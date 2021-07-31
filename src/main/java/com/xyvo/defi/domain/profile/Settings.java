package com.xyvo.defi.domain.profile;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.DOMAIN_SCHEMA;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "settings", schema = DOMAIN_SCHEMA)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Strategy buyStrategy;

    //manual or auto
    @Column(nullable = false)
    private Strategy sellStrategy;

    @Column(nullable = false)
    private Integer maxTrx;

    @Column
    private Integer minLiquidity;

    @Column
    private Integer autoSellPercent;

    @Column(nullable = false)
    private Double trxValue;

    @Column(nullable = false)
    private Integer slippage;

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

    public Integer getAutoSellPercent() {
        return autoSellPercent;
    }

    public void setAutoSellPercent(Integer autoSellPercent) {
        this.autoSellPercent = autoSellPercent;
    }

    public static Settings getDefaultSettings() {
        Settings settings = new Settings();
        settings.userId = null;
        settings.lockedLiquidityOnly = true;
        settings.buyStrategy = Strategy.MANUAL;
        settings.sellStrategy = Strategy.MANUAL;
        settings.minLiquidity = 1000;
        settings.autoSellPercent = 50;
        settings.maxTrx = 100;
        settings.trxValue = 2D;
        settings.slippage = 20;
        return settings;
    }
}
