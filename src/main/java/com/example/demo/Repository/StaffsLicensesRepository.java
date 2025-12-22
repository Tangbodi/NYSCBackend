package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsLicenses;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE staffs_licenses
    SET license_name = :licenseName,
        license_number = :licenseNumber,
        license_state = :licenseState,
        issue_date = :issueDate,
        expired_date = :expiredDate,
        notes = :notes,
        modified_at = NOW()
    WHERE staff_license_id = :licenseId
      AND staff_id = :staffId
    """, nativeQuery = true)
    int updateStaffLicenseByStaffIdAndLicenseId(
            @Param("licenseId") Long licenseId,
            @Param("staffId") Long staffId,
            @Param("licenseName") String licenseName,
            @Param("licenseNumber") String licenseNumber,
            @Param("licenseState") String licenseState,
            @Param("issueDate") String issueDate,
            @Param("expiredDate") String expiredDate,
            @Param("notes") String notes
    );


}
