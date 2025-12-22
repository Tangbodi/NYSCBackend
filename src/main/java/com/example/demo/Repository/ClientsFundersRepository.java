package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsFunders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClientsFundersRepository extends JpaRepository<ClientsFunders, Long> {
    @Query(value = "SELECT *\n" +
            "FROM nysc.clients_funders cf\n" +
            "WHERE client_id = :id", nativeQuery = true)
    List<Map<String, Object>> findFundersByClientId(@Param("id") Long clientId);

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE clients_funders
    SET payer_name = :payerName,
        plan_name = :planName,
        member_id = :memberId,
        group_number = :groupNumber,
        relationship_to_client = :relationshipToClient,
        policy_holder_name = :policyHolderName,
        policy_holder_phone = :policyHolderPhone,
        policy_holder_email = :policyHolderEmail,
        policy_holder_address = :policyHolderAddress,
        policy_holder_city = :policyHolderCity,
        policy_holder_state = :policyHolderState,
        policy_holder_zip_code = :policyHolderZipCode,
        coverage_order = :coverageOrder,
        effective_start = :effectiveStart,
        effective_end = :effectiveEnd,
        is_active = :isActive,
        notes = :notes,
        modified_at = NOW()
    WHERE client_id = :clientId
      AND client_funder_id = :funderId
    """, nativeQuery = true)
    int UpdateClientsFunderByClientIdAndFunderId(
            @Param("clientId") Long clientId,
            @Param("funderId") Long funderId,
            @Param("payerName") String payerName,
            @Param("planName") String planName,
            @Param("memberId") String memberId,
            @Param("groupNumber") String groupNumber,
            @Param("relationshipToClient") String relationshipToClient,
            @Param("policyHolderName") String policyHolderName,
            @Param("policyHolderPhone") String policyHolderPhone,
            @Param("policyHolderEmail") String policyHolderEmail,
            @Param("policyHolderAddress") String policyHolderAddress,
            @Param("policyHolderCity") String policyHolderCity,
            @Param("policyHolderState") String policyHolderState,
            @Param("policyHolderZipCode") String policyHolderZipCode,
            @Param("coverageOrder") String coverageOrder,
            @Param("effectiveStart") String effectiveStart,
            @Param("effectiveEnd") String effectiveEnd,
            @Param("isActive") String isActive,
            @Param("notes") String notes
    );


}
