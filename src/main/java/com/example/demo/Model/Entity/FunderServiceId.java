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
public class FunderServiceId implements Serializable {
    private static final long serialVersionUID = 7364829104756382910L;

    @NotNull
    @Column(name = "funder_id", nullable = false)
    private Integer funderId;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FunderServiceId entity = (FunderServiceId) o;
        return Objects.equals(this.funderId, entity.funderId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(funderId, serviceId);
    }
}
