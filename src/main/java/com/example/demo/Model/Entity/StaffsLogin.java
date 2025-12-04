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
    @Table(name = "staffs_login")
    public class StaffsLogin {
        @Id
        @Column(name = "staff_id", nullable = false)
        private Long id;

        @Size(max = 31)
        @NotNull
        @Column(name = "username", nullable = false, length = 31)
        private String username;

        @Size(max = 63)
        @NotNull
        @Column(name = "password", nullable = false, length = 63)
        private String password;
        @NotNull
        @Size(max = 1)
        @Column(name = "is_admin", length = 1)
        private String isAdmin;

        @NotNull
        @Column(name = "created_at", nullable = false)
        private Instant createdAt;

        @NotNull
        @Column(name = "modified_at", nullable = false)
        private Instant modifiedAt;
}
