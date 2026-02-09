package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StaffsInfoRepository extends JpaRepository<StaffsInfo, Long> {
    Optional<StaffsInfo> findByUsername(String username);
    Optional<StaffsInfo> findByEmail(String email);

    @Query(value = "SELECT *\n" +
            "FROM nysc.staffs_info si\n" +
            "WHERE client_id = :id", nativeQuery = true)
    List<Map<String, Object>> findStaffsByClientId(@Param("id") Long clientId);
}
