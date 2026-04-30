package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "client_funders")
public class ClientFunders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @NotNull
    @Column(name = "funder_id", nullable = false)
    private Integer funderId;
    @Size(max = 15)
    @NotNull
    @Column(name = "insurance_id", nullable = false, length = 15)
    private String insuranceId;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
