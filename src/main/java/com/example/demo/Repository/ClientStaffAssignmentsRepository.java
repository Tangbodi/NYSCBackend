package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientStaffId;
import com.example.demo.Model.Entity.ClientStaffAssignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStaffAssignmentsRepository extends JpaRepository<ClientStaffAssignments, ClientStaffId> {

}
