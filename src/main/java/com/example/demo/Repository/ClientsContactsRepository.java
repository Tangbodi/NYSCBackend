package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsContacts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsContactsRepository extends JpaRepository<ClientsContacts, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE clients_contacts 
        SET first_name = :firstName,
            last_name = :lastName,
            middle_name = :middleName,
            relationship_type = :relationshipType,
            is_primary = :isPrimary,
            phone = :phone,
            email = :email,
            address = :address,
            city = :city,
            state = :state,
            zip_code = :zipCode,
            notes = :notes,
            modified_at = NOW()
        WHERE client_id = :id
        """, nativeQuery = true)
    int UpdateClientsContacts(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("relationshipType") String relationshipType,
            @Param("isPrimary") String isPrimary,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("address") String address,
            @Param("city") String city,
            @Param("state") String state,
            @Param("zipCode") String zipCode,
            @Param("notes") String notes
    );



}
