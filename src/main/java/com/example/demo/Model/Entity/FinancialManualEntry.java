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
@Table(name = "financial_manual_entries")
public class FinancialManualEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id", nullable = false)
    private Long id;

    @Size(max = 15)
    @NotNull
    @Column(name = "entry_date", nullable = false, length = 15)
    private String entryDate;

    @Size(max = 512)
    @NotNull
    @Column(name = "section", nullable = false, length = 512)
    private String section;

    @Size(max = 100)
    @NotNull
    @Column(name = "item", nullable = false, length = 100)
    private String item;

    @Size(max = 100)
    @NotNull
    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
