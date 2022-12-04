package com.xyvo.defi.domain.profile;

import com.xyvo.defi.domain.transactions.Active;
import com.xyvo.defi.domain.transactions.History;

import javax.persistence.*;

import java.util.List;

import static com.xyvo.defi.utils.DomainUtils.*;

@Entity
@Table(name = "address", schema = DOMAIN_SCHEMA)
public class Address {

//    @Transient
//    private static final transient String ID_GEN = "id_gen2";
//
//    @Id
//    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = ADDRESS_LENGTH, unique = true, nullable = false)
    private String hex;

    @Column(nullable = false)
    private Long balance;

    @Column(nullable = false)
    private Integer networkId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(targetEntity = Active.class, mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Active> actives;

    @OneToMany(targetEntity = History.class, mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<History> histories;


    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }
}
