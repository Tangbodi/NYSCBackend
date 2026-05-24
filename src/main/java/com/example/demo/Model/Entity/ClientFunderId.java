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
public class ClientFunderId implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @NotNull
    @Column(name = "funder_id", nullable = false)
    private Integer funderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClientFunderId entity = (ClientFunderId) o;
        return Objects.equals(this.clientId, entity.clientId) &&
                Objects.equals(this.funderId, entity.funderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, funderId);
    }
}
