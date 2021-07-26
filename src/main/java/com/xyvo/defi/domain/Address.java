package com.xyvo.defi.domain;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;

@Entity
@Table(name = "Address", schema = DOMAIN_SCHEMA)
public class Address {

    @Transient
    private static final transient String ID_GEN = "id_gen2";

    @Id
    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)
    private Long id;

    @Column(length = ADDRESS_LENGTH, unique = true, nullable = false)
    private String hex;

    @Column(nullable = false)
    private Double balance = 0D;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
