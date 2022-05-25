package com.hotel.mgmt.domain.checkin;

import com.hotel.mgmt.domain.Customer;
import com.hotel.mgmt.domain.Employee;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PHIEU_THUE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RentalForm implements Serializable {

    private static final long serialVersionUID = -9114267485699674633L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_PHIEU_THUE")
    @SequenceGenerator(name = "ID_SEQ_PHIEU_THUE", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_PHIEU_THUE")
    @Column(name = "MA_PHIEU_THUE")
    private Long id;

    @NotNull
    @Column(name = "NGAYGIO_LAP")
    private Instant createdAt;

    @NotNull
    @Column(name = "NGAYGIO_CHECKIN")
    private Instant checkInDateTime;

    @NotNull
    @Column(name = "NGAYGIO_CHECKOUT")
    private Instant checkOutDateTime;

    @Min(0)
    @Column(name = "SO_TIEN_GIAM")
    private long discount;

    @Column(name = "GHI_CHU")
    private String note;

    // Phieu thue co the khong co ma phieu dat neu khach thue truc tiep
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_PHIEU_DAT")
    private ReservationForm reservationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV_LAP", nullable = false)
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMND", nullable = false)
    private Customer customer;

    @Size(min = 1)
    @OneToMany(mappedBy = "rentalForm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<RentalFormLine> lines = new ArrayList<>();

    public RentalForm() {}

    public Long getId() {
        return id;
    }

    public RentalForm setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public RentalForm setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getCheckInDateTime() {
        return checkInDateTime;
    }

    public RentalForm setCheckInDateTime(Instant checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
        return this;
    }

    public Instant getCheckOutDateTime() {
        return checkOutDateTime;
    }

    public RentalForm setCheckOutDateTime(Instant checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
        return this;
    }

    public long getDiscount() {
        return discount;
    }

    public RentalForm setDiscount(long discount) {
        this.discount = discount;
        return this;
    }

    public String getNote() {
        return note;
    }

    public RentalForm setNote(String note) {
        this.note = note;
        return this;
    }

    public ReservationForm getCheckInForm() {
        return reservationForm;
    }

    public RentalForm setCheckInForm(ReservationForm reservationForm) {
        this.reservationForm = reservationForm;
        return this;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public RentalForm setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public RentalForm setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public List<RentalFormLine> getLines() {
        return lines;
    }

    public RentalForm setLines(List<RentalFormLine> rentalFormLines) {
        this.lines = rentalFormLines;
        return this;
    }

    public void addLine(RentalFormLine line) {
        lines.add(line);
        line.setRentalForm(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalForm)) return false;
        RentalForm that = (RentalForm) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RentalForm{" +
            "id=" +
            id +
            ", createdAt=" +
            createdAt +
            ", checkInDateTime=" +
            checkInDateTime +
            ", checkOutDateTime=" +
            checkOutDateTime +
            ", discount=" +
            discount +
            ", note='" +
            note +
            '\'' +
            ", reservationForm=" +
            reservationForm +
            ", createdBy=" +
            createdBy.getAccount().getFullName() +
            ", customer=" +
            customer.getAccount().getFullName() +
            '}'
        );
    }
}
