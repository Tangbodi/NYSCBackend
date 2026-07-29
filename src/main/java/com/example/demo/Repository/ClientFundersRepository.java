package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientFunders;
import com.example.demo.Model.Entity.ClientFunderId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Repository
public interface ClientFundersRepository extends JpaRepository<ClientFunders, ClientFunderId> {

    @Query(value = """
        SELECT
            cf.client_id,
            cf.funder_id,
            cf.insurance_id,
            cf.relationship,
            cf.start_date,
            cf.end_date,
            cf.first_name,
            cf.last_name,
            cf.coverage_type,
            cf.created_at,
            cf.modified_at,
            ci.client_first_name,
            ci.client_last_name,
            ci.client_middle_name,
            ci.date_of_birth,
            ci.gender,
            ci.status,
            fs.funder_type,
            fs.funder_name,
            fs.address       AS funder_address,
            fs.coverage_type,
            fs.vendor_id,
            fs.phone,
            fs.email,
            fs.fax,
            fs.default_billing_provider
        FROM client_funders cf
        LEFT JOIN clients_info    ci ON cf.client_id  = ci.client_id
        LEFT JOIN funder_settings fs ON cf.funder_id  = fs.funder_id
        WHERE cf.client_id = :id
        """, nativeQuery = true)
    List<Map<String, Object>> findFundersByClientId(@Param("id") Long clientId);

    @Query(value = """
        SELECT DISTINCT
            sl.service_id,
            sl.billing_code,
            sl.rate_per_unit,
            sl.unit_type,
            sl.service,
            sl.description,
            sl.inactive,
            sl.created_at,
            sl.modified_at
        FROM client_funders cf
        JOIN funder_service_map fsm ON cf.funder_id  = fsm.funder_id
        JOIN service_lines     sl  ON fsm.service_id = sl.service_id
        WHERE cf.client_id = :clientId
        """, nativeQuery = true)
    List<Map<String, Object>> findServicesByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE client_funders
        SET insurance_id = :insuranceId,
            relationship = :relationship,
            start_date = :startDate,
            end_date = :endDate,
            first_name = :firstName,
            last_name = :lastName,
            coverage_type = :coverageType,
            modified_at = :modifiedAt
        WHERE client_id = :clientId
          AND funder_id = :funderId
        """, nativeQuery = true)
    int UpdateClientsFunderByClientIdAndFunderId(
            @Param("clientId") Long clientId,
            @Param("funderId") Integer funderId,
            @Param("insuranceId") String insuranceId,
            @Param("relationship") String relationship,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("coverageType") String coverageType,
            @Param("modifiedAt") Instant modifiedAt
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_funders WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_funders WHERE client_id = :clientId AND funder_id = :funderId", nativeQuery = true)
    void deleteByClientIdAndFunderId(@Param("clientId") Long clientId, @Param("funderId") Integer funderId);
}
