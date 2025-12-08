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

@Repository
public interface StaffsPayrollRepository extends JpaRepository<StaffsPayroll, Long> {
    @Transactional
    @Modifying
    @Query(value = """
        UPDATE staffs_payroll
        SET hourly_rate = :hourlyRate,
            pay_code = :payCode,
            effective_start_date = :effectiveStartDate,
            effective_end_date = :effectiveEndDate,
            notes = :notes,
            modified_at = NOW()
        WHERE staff_id = :staffId
        """, nativeQuery = true)
    int updateStaffsPayroll(
            @Param("staffId") Long staffId,
            @Param("hourlyRate") BigDecimal hourlyRate,
            @Param("payCode") String payCode,
            @Param("effectiveStartDate") String effectiveStartDate,
            @Param("effectiveEndDate") String effectiveEndDate,
            @Param("notes") String notes
    );
}

