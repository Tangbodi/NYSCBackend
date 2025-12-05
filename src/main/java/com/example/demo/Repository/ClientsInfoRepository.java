package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsInfoRepository extends JpaRepository<ClientsInfo, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE clients_info 
        SET client_first_name = :firstName,
            client_last_name = :lastName,
            client_middle_name = :middleName,
            date_of_birth = :dob,
            gender = :gender,
            status = :status,
            address = :address,
            city = :city,
            state = :state,
            zip_code = :zipCode,
            notes = :notes,
            modified_at = NOW()
        WHERE client_id = :id
        """, nativeQuery = true)
    int UpdateClientsInfo(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("dob") String dob,  // keep as String since DTO has String
            @Param("gender") String gender,
            @Param("status") String status,
            @Param("address") String address,
            @Param("city") String city,
            @Param("state") String state,
            @Param("zipCode") String zipCode,
            @Param("notes") String notes
    );
}



