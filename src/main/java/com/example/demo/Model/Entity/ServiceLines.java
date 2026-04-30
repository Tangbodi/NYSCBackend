package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "service_lines")
public class ServiceLines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Integer id;

    @Size(max = 15)
    @NotNull
    @Column(name = "billing_code", nullable = false, length = 15)
    private String billingCode;

    @Size(max = 15)
    @NotNull
    @Column(name = "rate_per_unit", nullable = false, length = 15)
    private String ratePerUnit;

    @Size(max = 15)
    @NotNull
    @Column(name = "unit_type", nullable = false, length = 15)
    private String unitType;

    @Size(max = 127)
    @NotNull
    @Column(name = "service", nullable = false, length = 127)
    private String service;

    @Size(max = 127)
    @NotNull
    @Column(name = "description", nullable = false, length = 127)
    private String description;

    @Size(max = 1)
    @Column(name = "inactive", length = 1)
    private String inactive;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
