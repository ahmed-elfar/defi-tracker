package com.xyvo.defi.domain.netwrok;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "dex", schema = NETWORK_SCHEMA)
public class Dex {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "network_id", nullable = false, insertable = false, updatable = false)
    private Integer networkId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = ADDRESS_LENGTH, unique = true, nullable = false)
    private String address;

    @Column(length = TOKEN_SYMBOL_LENGTH, nullable = false)
    private String version;

    @Column(length = TOKEN_SYMBOL_LENGTH)
    private String tokenSymbol;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(targetEntity = Network.class, fetch = FetchType.LAZY/*, cascade = CascadeType.DETACH*/)
    @JoinColumn(name = "network_id", referencedColumnName = "chainId")
    Network netWork;


    public Dex() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public Network getNetWork() {
        return netWork;
    }

    public void setNetWork(Network netWork) {
        this.netWork = netWork;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    @Override
    public String toString() {
        return "Dex{" +
                "id=" + id +
                ", network_id=" + networkId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", version='" + version + '\'' +
                ", tokenSymbol='" + tokenSymbol + '\'' +
                ", status=" + status +
                ", netWork=" + netWork +
                '}';
    }
}
