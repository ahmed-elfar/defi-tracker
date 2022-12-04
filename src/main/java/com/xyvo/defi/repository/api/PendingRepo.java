package com.xyvo.defi.repository.api;

import com.xyvo.defi.domain.transactions.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PendingRepo extends JpaRepository<Pending, Long> {

    String INSERT_QUERY = "INSERT INTO TRANSACTIONS.PENDING_TRANSACTION " +
            "(ID, CREATED, UPDATED, ATTEMPTS, CURRENT_PRICE, DEX_ID, EXCHANGE_EVERY, NETWORK_ID, STATUS, TOKEN_ADDRESS, " +
            "TOKEN_NAME, TOKEN_SYMBOL, TOTAL_LIQUIDITY) " +
            "VALUES (:id, :created, :updated, :attempts, :current_price, :dex_id, :exchange_every, :network_id, :status," +
            " :token_address, :token_name, :token_symbol, :total_liquidity)";

    /**
     * Custom insert method
     * @param id
     * @param created
     * @param updated
     * @param attempts
     * @param current_price
     * @param dex_id
     * @param exchange_every
     * @param network_id
     * @param status
     * @param token_address
     * @param token_name
     * @param token_symbol
     * @param total_liquidity
     */
    @Modifying
    @Query(value = INSERT_QUERY, nativeQuery = true)
    @Transactional
    void insert(@Param("id") Long id, @Param("created") Timestamp created, @Param("updated") Timestamp updated, @Param("attempts") Integer attempts,
                @Param("current_price") Double current_price, @Param("dex_id") Integer dex_id, @Param("exchange_every") Integer exchange_every,
                @Param("network_id") Integer network_id, @Param("status") Integer status, @Param("token_address") String token_address,
                @Param("token_name") String token_name, @Param("token_symbol") String token_symbol, @Param("total_liquidity") Double total_liquidity);

    @Transactional(readOnly = true)
    List<Pending> findByNetworkId(Integer id);

}
