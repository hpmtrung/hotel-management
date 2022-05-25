package com.hotel.mgmt.domain.checkout;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "PHUONG_THUC_THANH_TOAN")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PayMethod implements Serializable {

    private static final long serialVersionUID = -4511188140909147475L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_PHUONG_THUC_TT")
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @NaturalId
    @Column(name = "TEN", unique = true)
    private String name;

    public PayMethod() {}

    public Integer getId() {
        return id;
    }

    public PayMethod setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PayMethod setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayMethod payMethod = (PayMethod) o;
        return name.equals(payMethod.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PayMethod{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
