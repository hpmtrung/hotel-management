package com.hotel.mgmt.domain.service;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.Employee;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Embeddable
class ServicePriceTrackId implements Serializable {

    private static final long serialVersionUID = -4373669460412703883L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_DICH_VU", referencedColumnName = "MA_DICH_VU")
    private Service service;

    @NotNull
    @Column(name = "NGAYGIO_AP_DUNG")
    private Instant appliedAt;

    public ServicePriceTrackId() {}

    public ServicePriceTrackId(Service service, Instant appliedAt) {
        this.service = service;
        this.appliedAt = appliedAt;
    }

    public Service getService() {
        return service;
    }

    public ServicePriceTrackId setService(Service service) {
        this.service = service;
        return this;
    }

    public Instant getAppliedAt() {
        return appliedAt;
    }

    public ServicePriceTrackId setAppliedAt(Instant appliedAt) {
        this.appliedAt = appliedAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicePriceTrackId that = (ServicePriceTrackId) o;
        return service.equals(that.service) && appliedAt.equals(that.appliedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, appliedAt);
    }

    @Override
    public String toString() {
        return "ServicePriceTrackId{" + "serviceName=" + service.getName() + ", appliedAt=" + appliedAt + '}';
    }
}

@Entity
@Table(name = "BANG_GIA_DICH_VU")
public class ServicePriceTrack implements Serializable {

    private static final long serialVersionUID = 1818149551123740209L;

    @EmbeddedId
    private ServicePriceTrackId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV", nullable = false)
    private Employee editedBy;

    @Min(0)
    @Column(name = "GIA_DICH_VU")
    private long price;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    public ServicePriceTrack() {}

    public ServicePriceTrackId getId() {
        return id;
    }

    public ServicePriceTrack setId(ServicePriceTrackId id) {
        this.id = id;
        return this;
    }

    public Employee getEditedBy() {
        return editedBy;
    }

    public ServicePriceTrack setEditedBy(Employee editedBy) {
        this.editedBy = editedBy;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public ServicePriceTrack setPrice(long price) {
        this.price = price;
        return this;
    }

    public int getVat() {
        return vat;
    }

    public ServicePriceTrack setVat(int vat) {
        this.vat = vat;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicePriceTrack)) return false;
        ServicePriceTrack that = (ServicePriceTrack) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "ServicePriceTrack{" +
            "id=" +
            id +
            ", editedBy=" +
            editedBy.getAccount().getFullName() +
            ", price=" +
            price +
            ", vat=" +
            vat +
            '}'
        );
    }
}
