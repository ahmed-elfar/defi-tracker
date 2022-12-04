package com.xyvo.defi.domain.transactions;

import com.xyvo.defi.domain.profile.Audited;
import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;

@MappedSuperclass
public abstract class Transaction extends Audited {

//    @Transient
//    private static final transient String ID_GEN = "id_gen4";
//
//    @Id
//    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)

    public enum Status {

        PENDING("Pending"),
        ACTIVE("Active"),
        CLOSED("Closed"),
        FAILED("Failed"),
        RUGGED("Rugged"),
        BURNED("Burned"),
        UNTRACKED("UnTracked"),
        UNKNOWN("Unknown");

        public final String trxStatus;

        Status(String trxStatus) {
            this.trxStatus = trxStatus;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected Integer networkId;

    @Column(nullable = false)
    protected Integer dexId;

    @Column(length = ADDRESS_LENGTH, /*unique = true,*/ nullable = false)
    protected String tokenAddress;

    @Column(length = TOKEN_SYMBOL_LENGTH, nullable = false)
    protected String tokenSymbol;

    @Column(length = TOKEN_NAME_LENGTH, nullable = false)
    protected String tokenName;

    @Column(nullable = false)
    protected Double currentPrice;

    @Column(nullable = false)
    protected Double totalLiquidity;

    @Column(nullable = false)
    protected Status status = Status.UNKNOWN;

    @Column()
    protected Integer exchangeEvery;

    @Column()
    protected Integer attempts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    public Integer getDexId() {
        return dexId;
    }

    public void setDexId(Integer dexId) {
        this.dexId = dexId;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
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
