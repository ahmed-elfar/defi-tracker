package com.xyvo.defi.domain.transactions;

import com.xyvo.defi.domain.profile.Audited;
import com.xyvo.defi.domain.profile.User;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;

@Entity
@Table(name = "transaction", schema = TRANSACTIONS_SCHEMA)
public class Transaction extends Audited {

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
        RUGGED("Rugged");

        public final String trxStatus;

        Status(String trxStatus) {
            this.trxStatus = trxStatus;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = ADDRESS_LENGTH, /*unique = true,*/ nullable = false)
    private String tokenAddress;

    @Column(length = TOKEN_SYMBOL_LENGTH, nullable = false)
    private String tokenSymbol;

    @Column(length = TOKEN_NAME_LENGTH, nullable = false)
    private String tokenName;

    @Column(nullable = false)
    private Double openPrice;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(nullable = false)
    private Double closedPrice;

    @Column(nullable = false)
    private Double totalLiquidity;

    @Column(nullable = false)
    private Status status;


//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;
//
//    @Column(nullable = false)
//    private Double totalLiquidity;



    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
