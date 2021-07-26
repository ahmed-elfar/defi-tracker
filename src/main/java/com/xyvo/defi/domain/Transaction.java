package com.xyvo.defi.domain;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;

@Entity
@Table(name = "Transaction", schema = TRANSACTIONS_SCHEMA)
public class Transaction extends Audited {

    @Transient
    private static final transient String ID_GEN = "id_gen4";

    @Id
    @SequenceGenerator(name = ID_GEN, sequenceName = ID_GEN, allocationSize = DEFAULT_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)
    private Long id;

    @Column(length = 42, unique = true, nullable = false)
    private String tokenAddress;

    @Column(nullable = false)
    private Double value = 0D;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
