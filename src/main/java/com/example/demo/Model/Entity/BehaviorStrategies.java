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
@Table(name = "behavior_strategies")
public class BehaviorStrategies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_id", nullable = false)
    private Integer id;

    @Size(max = 63)
    @NotNull
    @Column(name = "behavior_strategy", nullable = false, length = 63)
    private String behaviorStrategy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
