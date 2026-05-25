package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ClientReferringProviderId implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @NotNull
    @Column(name = "npi_number", nullable = false, length = 15)
    private String npiNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClientReferringProviderId entity = (ClientReferringProviderId) o;
        return Objects.equals(this.clientId, entity.clientId) &&
                Objects.equals(this.npiNumber, entity.npiNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, npiNumber);
    }
}
