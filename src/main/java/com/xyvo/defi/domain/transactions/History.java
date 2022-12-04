package com.xyvo.defi.domain.transactions;

import com.xyvo.defi.domain.profile.Address;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.TRANSACTIONS_SCHEMA;

@Entity
@Table(name = "history_transaction", schema = TRANSACTIONS_SCHEMA)
@AttributeOverride(name = "currentPrice", column = @Column(name = "closedPrice"))
public class History extends Transaction {

    @Column(nullable = false)
    protected Double openPrice;

    public History() {
        this.status = Status.CLOSED;
    }

    @ManyToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
