package com.example.demo.Repository;


import com.example.demo.Model.Entity.StaffsPayroll;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Repository
public interface StaffsPayrollRepository extends JpaRepository<StaffsPayroll, Long> {

    // Full payroll/rate history for a staff member (staff_id is no longer the
    // primary key — a staff can have multiple rows over time via effective dates),
    // most recent record first.
    List<StaffsPayroll> findByStaffIdOrderByIdDesc(Long staffId);

    // Updates one specific payroll/rate row by its own id — staff_id is no longer
    // unique, so updating by staff_id would touch every history row for that staff.
    @Transactional
    @Modifying
    @Query(value = """
        UPDATE staffs_payroll
        SET hourly_rate = :hourlyRate,
            pay_code = :payCode,
            effective_start_date = :effectiveStartDate,
            effective_end_date = :effectiveEndDate,
            notes = :notes,
            modified_at = :modifiedAt
        WHERE id = :id
        """, nativeQuery = true)
    int updateStaffsPayroll(
            @Param("id") Long id,
            @Param("hourlyRate") BigDecimal hourlyRate,
            @Param("payCode") String payCode,
            @Param("effectiveStartDate") String effectiveStartDate,
            @Param("effectiveEndDate") String effectiveEndDate,
            @Param("notes") String notes,
            @Param("modifiedAt") Instant modifiedAt
    );
}
