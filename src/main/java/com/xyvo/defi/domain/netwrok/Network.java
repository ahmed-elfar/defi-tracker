package com.xyvo.defi.domain.netwrok;

import javax.persistence.*;

import java.util.List;

import static com.xyvo.defi.utils.DomainUtils.NETWORK_SCHEMA;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "network", schema = NETWORK_SCHEMA)
public class Network {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer chainId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String rpcUrl;

    @Column(length = 10, nullable = false)
    private String symbol;

    @OneToMany(targetEntity = Dex.class, mappedBy = "netWork", fetch = FetchType.LAZY)
    List<Dex> dexList;


    public Network() {
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRpcUrl() {
        return rpcUrl;
    }

    public void setRpcUrl(String rpcUrl) {
        this.rpcUrl = rpcUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Dex> getDexList() {
        return dexList;
    }
}
