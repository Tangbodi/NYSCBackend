package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientProgramAssignments;
import com.example.demo.Model.Entity.ClientProgramId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientProgramAssignmentsRepository extends JpaRepository<ClientProgramAssignments, ClientProgramId> {

    List<ClientProgramAssignments> findByIdClientId(Long clientId);
}
