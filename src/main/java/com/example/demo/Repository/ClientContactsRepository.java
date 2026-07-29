package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientContacts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ClientContactsRepository extends JpaRepository<ClientContacts, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_contacts WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE client_contacts
        SET first_name = :firstName,
            last_name = :lastName,
            relationship_type = :relationshipType,
            is_primary = :isPrimary,
            phone = :phone,
            email = :email,
            address = :address,
            city = :city,
            state = :state,
            zip_code = :zipCode,
            modified_at = :modifiedAt
        WHERE client_id = :id
        """, nativeQuery = true)
    int UpdateClientsContacts(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("relationshipType") String relationshipType,
            @Param("isPrimary") String isPrimary,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("address") String address,
            @Param("city") String city,
            @Param("state") String state,
            @Param("zipCode") String zipCode,
            @Param("modifiedAt") Instant modifiedAt
    );

}
