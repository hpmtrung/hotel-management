package com.hotel.mgmt.domain.service;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.Employee;
import com.hotel.mgmt.domain.checkin.RentalFormLine;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SU_DUNG_THEM_DICH_VU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceCustomerUsage implements Serializable {

    private static final long serialVersionUID = -1692070958420751610L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_SDTDV")
    @SequenceGenerator(name = "ID_SEQ_SDTDV", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_SDTDV")
    @Column(name = "MA_SDDV")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_CT_PHIEU_THUE", nullable = false)
    private RentalFormLine rentalFormLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_DICH_VU", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV_LAP")
    private Employee createdBy;

    @NotNull
    @Column(name = "NGAYGIO_BAT_DAU_SD")
    private Instant startDateTime;

    @Min(0)
    @Column(name = "SO_LUONG")
    private int quantity;

    @Min(0)
    @Column(name = "GIA_DICH_VU")
    private long servicePrice;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    @Min(0)
    @Column(name = "TIEN_THANH_TOAN")
    private long total;

    @Column(name = "DA_THANH_TOAN")
    private boolean isPaid;

    @Size(max = 200)
    @Column(name = "GHI_CHU")
    private String note;

    public ServiceCustomerUsage() {}

    public Long getId() {
        return id;
    }

    public RentalFormLine getRentalFormLine() {
        return rentalFormLine;
    }

    public Service getService() {
        return service;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getServicePrice() {
        return servicePrice;
    }

    public int getVat() {
        return vat;
    }

    public long getTotal() {
        return total;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getNote() {
        return note;
    }

    public ServiceCustomerUsage setId(Long id) {
        this.id = id;
        return this;
    }

    public ServiceCustomerUsage setRentalFormLine(RentalFormLine rentalFormLine) {
        this.rentalFormLine = rentalFormLine;
        return this;
    }

    public ServiceCustomerUsage setService(Service service) {
        this.service = service;
        return this;
    }

    public ServiceCustomerUsage setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ServiceCustomerUsage setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public ServiceCustomerUsage setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ServiceCustomerUsage setServicePrice(long servicePrice) {
        this.servicePrice = servicePrice;
        return this;
    }

    public ServiceCustomerUsage setVat(int vat) {
        this.vat = vat;
        return this;
    }

    public ServiceCustomerUsage setTotal(long total) {
        this.total = total;
        return this;
    }

    public ServiceCustomerUsage setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public ServiceCustomerUsage setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceCustomerUsage)) return false;
        ServiceCustomerUsage that = (ServiceCustomerUsage) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "ServiceCustomerUsage{" +
            "id=" +
            id +
            ", rentalFormLineId=" +
            rentalFormLine.getId() +
            ", serviceName=" +
            service.getName() +
            ", createdById=" +
            (createdBy != null ? createdBy.getId() : "NULL") +
            ", startDateTime=" +
            startDateTime +
            ", quantity=" +
            quantity +
            ", servicePrice=" +
            servicePrice +
            ", vat=" +
            vat +
            ", total=" +
            total +
            ", isPaid=" +
            isPaid +
            ", note='" +
            note +
            '\'' +
            '}'
        );
    }
}
