package com.xyvo.defi.domain.transactions;

import javax.persistence.*;

import static com.xyvo.defi.utils.DomainUtils.*;

@Entity
@Table(name = "pending_transaction", schema = TRANSACTIONS_SCHEMA)
public class Pending extends Transaction {

    public Pending() {
        this.status = Status.PENDING;
    }
}
