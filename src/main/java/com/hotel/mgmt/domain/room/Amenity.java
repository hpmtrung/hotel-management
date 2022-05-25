package com.hotel.mgmt.domain.room;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "TIEN_NGHI")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Amenity implements Serializable {

    private static final long serialVersionUID = -6443692771663971631L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_TIEN_NGHI")
    private int id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN", unique = true)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Column(name = "ICON", unique = true)
    private String icon;

    @ManyToMany(mappedBy = "amenities")
    private Set<SuiteType> suiteTypes = new HashSet<>();

    public Amenity() {}

    public int getId() {
        return id;
    }

    public Amenity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Amenity setName(String name) {
        this.name = name;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Amenity setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Set<SuiteType> getSuiteTypes() {
        return suiteTypes;
    }

    public Amenity setSuiteTypes(Set<SuiteType> suiteTypes) {
        this.suiteTypes = suiteTypes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenity amenity = (Amenity) o;
        return name.equals(amenity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Amenity{" + "id=" + id + ", name='" + name + '\'' + ", icon='" + icon + '\'' + '}';
    }
}
