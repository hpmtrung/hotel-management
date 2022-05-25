package com.hotel.mgmt.domain.checkin;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "TRANG_THAI_PHIEU_DAT")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ReservationFormStatus implements Serializable {

    private static final long serialVersionUID = 8090438477607578722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_TRANG_THAI_PD")
    private Integer id;

    @NotBlank
    @NaturalId
    @Column(name = "TEN", unique = true)
    private String name;

    public ReservationFormStatus() {}

    public Integer getId() {
        return id;
    }

    public ReservationFormStatus setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ReservationFormStatus setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationFormStatus that = (ReservationFormStatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ReservationFormStatus{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
