package com.hotel.mgmt.domain.service;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "DICH_VU")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Service implements Serializable {

    private static final long serialVersionUID = 6097158302339387584L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_DICH_VU")
    @SequenceGenerator(name = "ID_SEQ_DICH_VU", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_DICH_VU")
    @Column(name = "MA_DICH_VU")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_LOAI_DV", nullable = false)
    private ServiceType serviceType;

    @NotBlank
    @NaturalId
    @Size(max = 100)
    @Column(name = "TEN", unique = true)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Column(name = "MO_TA")
    private String description;

    @NotBlank
    @Size(max = 50)
    @Column(name = "ICON")
    private String icon;

    @Min(0)
    @Column(name = "GIA")
    private long price;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DON_VI_TINH")
    private String unitName;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    public Service() {}

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Service setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Service setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Service setPrice(long price) {
        this.price = price;
        return this;
    }

    public String getUnitName() {
        return unitName;
    }

    public Service setUnitName(String priceUnit) {
        this.unitName = priceUnit;
        return this;
    }

    public int getVat() {
        return vat;
    }

    public Service setVat(int vat) {
        this.vat = vat;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Service setId(Integer id) {
        this.id = id;
        return this;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Service setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return name.equals(service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return (
            "Service{" +
            "id=" +
            id +
            ", serviceTypeName=" +
            serviceType.getName() +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", icon='" +
            icon +
            '\'' +
            ", price=" +
            price +
            ", unitName='" +
            unitName +
            '\'' +
            ", vat=" +
            vat +
            '}'
        );
    }
}
