package com.hotel.mgmt.domain.room;

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
@Table(name = "KIEU_PHONG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SuiteStyle implements Serializable {

    private static final long serialVersionUID = -48209910981190638L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_KIEU_PHONG")
    private int id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN", unique = true)
    private String name;

    @Size(max = 200)
    @Column(name = "MO_TA")
    private String description;

    @OneToMany(mappedBy = "suiteStyle", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<Suite> suites = new HashSet<>();

    public SuiteStyle() {}

    public int getId() {
        return id;
    }

    public SuiteStyle setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SuiteStyle setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SuiteStyle setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Suite> getSuites() {
        return suites;
    }

    public SuiteStyle setSuites(Set<Suite> suites) {
        this.suites = suites;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuiteStyle that = (SuiteStyle) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "SuiteStyle{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '}';
    }
}
