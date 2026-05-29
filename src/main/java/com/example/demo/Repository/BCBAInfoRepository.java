package com.example.demo.Repository;

import com.example.demo.Model.Entity.BCBAInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BCBAInfoRepository extends JpaRepository<BCBAInfo, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE bcba_info
        SET npi_number  = :npiNumber,
            medicaid_id = :medicaidId,
            modified_at = NOW()
        WHERE staff_id = :staffId
        """, nativeQuery = true)
    int updateBCBAInfo(
            @Param("staffId") Long staffId,
            @Param("npiNumber") String npiNumber,
            @Param("medicaidId") String medicaidId
    );
}
