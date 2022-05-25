package com.hotel.mgmt.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "LOAI_PHONG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SuiteType implements Serializable {

    private static final long serialVersionUID = -181460764975327490L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_LOAI_PHONG")
    private Integer id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN", unique = true)
    private String name;

    @Size(max = 200)
    @Column(name = "MO_TA")
    private String description;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "TIEN_NGHI_LOAI_PHONG",
        joinColumns = @JoinColumn(name = "MA_LOAI_PHONG"),
        inverseJoinColumns = @JoinColumn(name = "MA_TIEN_NGHI")
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Amenity> amenities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "suiteType", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<Suite> suites = new HashSet<>();

    public SuiteType() {}

    public Integer getId() {
        return id;
    }

    public SuiteType setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SuiteType setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SuiteType setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Suite> getSuites() {
        return suites;
    }

    public SuiteType setSuites(Set<Suite> suites) {
        this.suites = suites;
        return this;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public SuiteType setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public void addSuite(Suite suite) {
        suites.add(suite);
        suite.setSuiteType(this);
    }

    public void removeSuite(Suite suite) {
        suites.remove(suite);
        suite.setSuiteType(null);
    }

    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
        amenity.getSuiteTypes().add(this);
    }

    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
        amenity.getSuiteTypes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuiteType suiteType = (SuiteType) o;
        return name.equals(suiteType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "SuiteType{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
