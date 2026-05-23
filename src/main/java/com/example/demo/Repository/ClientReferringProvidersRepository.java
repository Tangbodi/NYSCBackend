package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientReferringProviders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReferringProvidersRepository extends JpaRepository<ClientReferringProviders, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE client_referring_providers
        SET provider_first_name = :firstName,
            provider_last_name = :lastName,
            provider_middle_name = :middleName,
            npi_number = :npiNumber,
            is_active = :isActive,
            taxonomy_code = :taxonomyCode,
            phone = :phone,
            fax = :fax,
            address = :address,
            city = :city,
            state = :state,
            zip_code = :zipCode,
            notes = :notes,
            modified_at = NOW()
        WHERE referring_provider_id = :providerId
          AND client_id = :clientId
        """, nativeQuery = true)
    int UpdateClientsReferringProviderByClientIdAndProviderId(
            @Param("providerId") Long providerId,
            @Param("clientId") Long clientId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("npiNumber") String npiNumber,
            @Param("isActive") String isActive,
            @Param("taxonomyCode") String taxonomyCode,
            @Param("phone") String phone,
            @Param("fax") String fax,
            @Param("address") String address,
            @Param("city") String city,
            @Param("state") String state,
            @Param("zipCode") String zipCode,
            @Param("notes") String notes
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_referring_providers WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);

}
