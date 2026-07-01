package com.example.demo.Model.Entity;

import jakarta.persistence.*;
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
@Table(name = "financial_donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id", nullable = false)
    private Long id;

    @Size(max = 15)
    @NotNull
    @Column(name = "donation_date", nullable = false, length = 15)
    private String donationDate;

    @Size(max = 512)
    @NotNull
    @Column(name = "donor", nullable = false, length = 512)
    private String donor;

    @Digits(integer = 10, fraction = 2)
    @NotNull
    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Size(max = 50)
    @NotNull
    @Column(name = "donation_type", nullable = false, length = 50)
    private String donationType;

    @Size(max = 512)
    @NotNull
    @Column(name = "note", nullable = false, length = 512)
    private String note;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
