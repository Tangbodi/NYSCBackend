package com.example.demo.Repository;

import com.example.demo.Model.Entity.ServiceLines;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ServiceLinesRepository extends JpaRepository<ServiceLines, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE service_lines
        SET billing_code  = :billingCode,
            rate_per_unit = :ratePerUnit,
            unit_type     = :unitType,
            service       = :service,
            description   = :description,
            inactive      = :inactive,
            start_date    = :startDate,
            end_date      = :endDate,
            modified_at   = :modifiedAt
        WHERE service_id = :id
        """, nativeQuery = true)
    int UpdateServiceLine(
            @Param("id") Integer id,
            @Param("billingCode") String billingCode,
            @Param("ratePerUnit") String ratePerUnit,
            @Param("unitType") String unitType,
            @Param("service") String service,
            @Param("description") String description,
            @Param("inactive") String inactive,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("modifiedAt") Instant modifiedAt

    );
}
