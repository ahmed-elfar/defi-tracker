package com.xyvo.defi.repository;

import com.xyvo.defi.domain.netwrok.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NetWorkRepo extends JpaRepository<Network, Integer> {

    String INSERT_QUERY = "INSERT INTO network.network (chain_id, name, rpc_url, symbol) " +
            "VALUES (:chainid, :name, :rpcurl, :symbol)";

    /**
     * Custom insert method
     * @param id
     * @param name
     * @param rpc
     * @param symbol
     */
    @Modifying
    @Query(value = INSERT_QUERY, nativeQuery = true)
    void insert(@Param("chainid") Integer id, @Param("name") String name, @Param("rpcurl") String rpc, @Param("symbol") String symbol);
}
