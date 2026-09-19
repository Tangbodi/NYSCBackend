package com.example.demo.Model.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "staffs_payroll")
public class StaffsPayroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Digits(integer = 4, fraction = 2)
    @NotNull
    @Column(name = "hourly_rate", nullable = false, precision = 6, scale = 2)
    private BigDecimal hourlyRate;

    @Size(max = 7)
    @Column(name = "pay_code", length = 7)
    private String payCode;

    @Size(max = 15)
    @Column(name = "effective_start_date", length = 15)
    private String effectiveStartDate;

    @Size(max = 15)
    @Column(name = "effective_end_date", length = 15)
    private String effectiveEndDate;

    @Size(max = 63)
    @Column(name = "notes", length = 63)
    private String notes;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
