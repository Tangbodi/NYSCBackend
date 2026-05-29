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

@Getter
@Setter
@Entity
@Table(name = "bcba_info")
public class BCBAInfo {
    @Id
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @Column(name = "npi_number", nullable = false, length = 31)
    private String npiNumber;

    @Size(max = 31)
    @Column(name = "medicaid_id", nullable = false, length = 31)
    private String medicaidId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
