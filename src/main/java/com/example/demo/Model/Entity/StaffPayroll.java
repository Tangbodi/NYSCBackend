package com.example.demo.Model.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "staff_payroll")
public class StaffPayroll {
    @Id
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Size(max = 8)
    @NotNull
    @Column(name = "hourly_rate", nullable = false, length = 8)
    private String hourlyRate;

    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    private String effectiveEndDate;

    @Size(max = 63)
    @NotNull
    @Column(name = "notes", nullable = false, length = 63)
    private String notes;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
