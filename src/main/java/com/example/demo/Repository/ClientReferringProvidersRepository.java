package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientReferringProviders;
import com.example.demo.Model.Entity.ClientReferringProviderId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ClientReferringProvidersRepository extends JpaRepository<ClientReferringProviders, ClientReferringProviderId> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE client_referring_providers
        SET provider_first_name = :firstName,
            provider_middle_name = :middleName,
            provider_last_name = :lastName,
            is_active = :isActive,
            taxonomy_code = :taxonomyCode,
            phone = :phone,
            fax = :fax,
            address = :address,
            city = :city,
            state = :state,
            zip_code = :zipCode,
            modified_at = :modifiedAt
        WHERE client_id = :clientId
          AND npi_number = :npiNumber
        """, nativeQuery = true)
    int UpdateClientsReferringProvider(
            @Param("clientId") Long clientId,
            @Param("npiNumber") String npiNumber,
            @Param("firstName") String firstName,
            @Param("middleName") String middleName,
            @Param("lastName") String lastName,
            @Param("isActive") String isActive,
            @Param("taxonomyCode") String taxonomyCode,
            @Param("phone") String phone,
            @Param("fax") String fax,
            @Param("address") String address,
            @Param("city") String city,
            @Param("state") String state,
            @Param("zipCode") String zipCode,
            @Param("modifiedAt") Instant modifiedAt
    );

    @Query(value = "SELECT * FROM client_referring_providers WHERE client_id = :clientId", nativeQuery = true)
    List<ClientReferringProviders> findAllByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_referring_providers WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_referring_providers WHERE client_id = :clientId AND npi_number = :npiNumber", nativeQuery = true)
    void deleteByClientIdAndNpiNumber(@Param("clientId") Long clientId, @Param("npiNumber") String npiNumber);
}
