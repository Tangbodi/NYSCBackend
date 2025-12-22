package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsLicenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StaffsLicensesRepository extends JpaRepository<StaffsLicenses, Long> {
    @Query(value = "SELECT *\n" +
            "FROM nysc.staffs_licenses sl\n" +
            "WHERE staff_id = :id", nativeQuery = true)
    List<Map<String, Object>> findLicensesByStaffId(@Param("id") Long staffId);
}
