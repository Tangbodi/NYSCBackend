package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientProgramAssignments;
import com.example.demo.Model.Entity.ClientProgramId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientProgramAssignmentsRepository extends JpaRepository<ClientProgramAssignments, ClientProgramId> {

    List<ClientProgramAssignments> findByIdClientId(Long clientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_program_assignments WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);
}
