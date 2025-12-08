package com.example.demo.Model.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "staffs_payroll")
public class StaffsPayroll {
    @Id
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Digits(integer = 6, fraction = 2)
    @NotNull
    @Column(name = "hourly_rate", nullable = false, precision = 8, scale = 2)
    private BigDecimal hourlyRate;

    @Size(max = 7)
    @Column(name = "pay_code", length = 7)
    @NotNull
    private String payCode;
    @Size(max = 15)
    @Column(name = "effective_start_date", length = 15)
    private String effectiveStartDate;

    @Size(max = 15)
    @Column(name = "effective_end_date", length = 15)
    private String effectiveEndDate;

    @Size(max = 63)
    @Column(name = "notes", nullable = false, length = 63)
    private String notes;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
