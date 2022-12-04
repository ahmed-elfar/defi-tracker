package com.xyvo.defi.domain.transactions;

import com.xyvo.defi.domain.profile.Address;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.TRANSACTIONS_SCHEMA;

@Entity
@Table(name = "active_transaction", schema = TRANSACTIONS_SCHEMA)
public class Active extends Transaction {

    @Column(nullable = false)
    protected Double openPrice;

    @Column()
    protected Double takeProfit;

    @Column()
    protected Double stopLoss;

    public Active() {
        this.status = Status.ACTIVE;
    }

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
