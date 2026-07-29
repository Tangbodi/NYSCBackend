package com.example.demo.Repository;

import com.example.demo.Model.Entity.FunderSettings;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface FunderSettingsRepository extends JpaRepository<FunderSettings, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE funder_settings
        SET funder_type              = :funderType,
            funder_name              = :funderName,
            address                  = :address,
            coverage_type            = :coverageType,
            vendor_id                = :vendorId,
            phone                    = :phone,
            email                    = :email,
            fax                      = :fax,
            default_billing_provider = :defaultBillingProvider,
            modified_at              = :modifiedAt
        WHERE funder_id = :id
        """, nativeQuery = true)
    int UpdateFunderSettings(
            @Param("id") Integer id,
            @Param("funderType") String funderType,
            @Param("funderName") String funderName,
            @Param("address") String address,
            @Param("coverageType") String coverageType,
            @Param("vendorId") String vendorId,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("fax") String fax,
            @Param("defaultBillingProvider") String defaultBillingProvider,
            @Param("modifiedAt") Instant modifiedAt

    );
}
