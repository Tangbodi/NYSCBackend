package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientStaffId;
import com.example.demo.Model.Entity.ClientStaffAssignments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ClientStaffAssignmentsRepository extends JpaRepository<ClientStaffAssignments, ClientStaffId> {

    @Query(value = """
                SELECT
                        ci.client_first_name,
                        ci.client_last_name,
                        si.staff_first_name,
                        si.staff_last_name
                    FROM nysc.client_staff_assignments csa
                    JOIN nysc.clients_info ci
                        ON csa.client_id = ci.client_id
                    JOIN nysc.staffs_info si
                        ON csa.staff_id = si.staff_id
            """, nativeQuery = true)
    List<Map<String, Object>> findAllClientStaffAssignments();

    @Query(value = """
        SELECT *
        FROM nysc.client_staff_assignments
        WHERE client_id = :clientId
        """, nativeQuery = true)
    List<Map<String, Object>> findAllStaffIdsByClientId(
            @Param("clientId") Long clientId
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_staff_assignments WHERE client_id = :clientId AND staff_id = :staffId", nativeQuery = true)
    int deleteByClientIdAndStaffId(@Param("clientId") Long clientId, @Param("staffId") Long staffId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM client_staff_assignments WHERE client_id = :clientId", nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);
}
