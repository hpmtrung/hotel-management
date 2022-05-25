package com.hotel.mgmt.domain.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "LOAI_DICH_VU")
@Immutable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ServiceType implements Serializable {

    private static final long serialVersionUID = -7209025040443615201L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_LOAI_DV")
    private Integer id;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN", unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceType", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private Set<Service> services = new HashSet<>();

    public ServiceType() {}

    public Integer getId() {
        return id;
    }

    public ServiceType setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceType setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Service> getServices() {
        return services;
    }

    public ServiceType setServices(Set<Service> services) {
        this.services = services;
        return this;
    }

    public void addService(Service service) {
        services.add(service);
        service.setServiceType(this);
    }

    public void removeService(Service service) {
        services.remove(service);
        service.setServiceType(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceType that = (ServiceType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ServiceType{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
