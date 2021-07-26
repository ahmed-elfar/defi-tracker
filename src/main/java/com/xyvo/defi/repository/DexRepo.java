package com.xyvo.defi.repository;

import com.xyvo.defi.domain.netwrok.Dex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DexRepo extends JpaRepository<Dex, Integer> {

    String INSERT_QUERY = "INSERT INTO network.dex (id, network_id, name, address, version, token_symbol, status) " +
            "VALUES (:id, :networkId, :dexName, :address, :version, :tokenSymbol, :status)";

    /**
     * Custom insert method
     * @param chainId
     * @param dexName
     * @param address
     * @param version
     * @param tokenSymbol
     * @param status
     */
    @Modifying
    @Query(value = INSERT_QUERY, nativeQuery = true)
    void insert(@Param("id") int chainId, @Param("networkId") int networkId, @Param("dexName") String dexName, @Param("address") String address, @Param("version") String version,
                @Param("tokenSymbol") String tokenSymbol, @Param("status") Boolean status);
}
