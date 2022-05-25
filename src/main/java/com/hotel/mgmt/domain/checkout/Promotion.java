package com.hotel.mgmt.domain.checkout;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "KHUYEN_MAI")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Promotion implements Serializable {

    private static final long serialVersionUID = -1895304856733021319L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_KHUYEN_MAI")
    @SequenceGenerator(name = "ID_SEQ_KHUYEN_MAI", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_KHUYEN_MAI")
    @Column(name = "MA_KHUYEN_MAI")
    private Long id;

    @NotBlank
    @NaturalId
    @Size(max = 200)
    @Column(name = "TEN", unique = true)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Column(name = "MO_TA")
    private String description;

    @NotNull
    @Column(name = "NGAYGIO_BD")
    private Instant startDateTime;

    @NotNull
    @Column(name = "NGAYGIO_KT")
    private Instant endDateTime;

    public Promotion() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public Promotion withId(Long id) {
        this.id = id;
        return this;
    }

    public Promotion withName(String name) {
        this.name = name;
        return this;
    }

    public Promotion withDescription(String description) {
        this.description = description;
        return this;
    }

    public Promotion withStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public Promotion withEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return name.equals(promotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return (
            "Promotion{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", startDateTime=" +
            startDateTime +
            ", endDateTime=" +
            endDateTime +
            '}'
        );
    }
}
