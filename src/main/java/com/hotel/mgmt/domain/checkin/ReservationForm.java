package com.hotel.mgmt.domain.checkin;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.Customer;
import com.hotel.mgmt.domain.Employee;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PHIEU_DAT")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservationForm implements Serializable {

    private static final long serialVersionUID = 999143806732123313L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_PHIEU_DAT")
    @SequenceGenerator(name = "ID_SEQ_PHIEU_DAT", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_PHIEU_DAT")
    @Column(name = "MA_PHIEU_DAT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMND", nullable = true)
    private Customer customer;

    @NotNull
    @Column(name = "NGAYGIO_LAP")
    private Instant createdAt;

    @Min(1)
    @Column(name = "SO_LUONG_KHACH")
    private int personNum;

    @Size(max = 300)
    @Column(name = "YEU_CAU_DAC_BIET")
    private String otherRequirements;

    @Min(0)
    @Column(name = "TIEN_DAT_COC")
    private long deposit;

    @Column(name = "NGAY_CHUYEN_TIEN_DC")
    private Instant completeDepositDate;

    @Size(max = 200)
    @Column(name = "NOI_DUNG_DAT_COC")
    private String depositContent;

    @NotNull
    @Column(name = "NGAY_CHECKIN")
    private Instant checkInDate;

    @NotNull
    @Column(name = "NGAY_CHECKOUT")
    private Instant checkOutDate;

    @PercentConstaint
    @Column(name = "PHAN_TRAM_PHAT")
    private int finePercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_TRANG_THAI_PD", nullable = false)
    private ReservationFormStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV_XAC_NHAN")
    private Employee confirmedBy;

    public ReservationForm() {}

    public Long getId() {
        return id;
    }

    public ReservationForm setId(Long id) {
        this.id = id;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ReservationForm setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ReservationForm setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public int getPersonNum() {
        return personNum;
    }

    public ReservationForm setPersonNum(int personNum) {
        this.personNum = personNum;
        return this;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public ReservationForm setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
        return this;
    }

    public long getDeposit() {
        return deposit;
    }

    public ReservationForm setDeposit(long deposit) {
        this.deposit = deposit;
        return this;
    }

    public Instant getCompleteDepositDate() {
        return completeDepositDate;
    }

    public ReservationForm setCompleteDepositDate(Instant completeDepositDate) {
        this.completeDepositDate = completeDepositDate;
        return this;
    }

    public String getDepositContent() {
        return depositContent;
    }

    public ReservationForm setDepositContent(String depositContent) {
        this.depositContent = depositContent;
        return this;
    }

    public Instant getCheckInDate() {
        return checkInDate;
    }

    public ReservationForm setCheckInDate(Instant checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public Instant getCheckOutDate() {
        return checkOutDate;
    }

    public ReservationForm setCheckOutDate(Instant checkOutDate) {
        this.checkOutDate = checkOutDate;
        return this;
    }

    public int getFinePercent() {
        return finePercent;
    }

    public ReservationForm setFinePercent(int finePercent) {
        this.finePercent = finePercent;
        return this;
    }

    public ReservationFormStatus getStatus() {
        return status;
    }

    public ReservationForm setStatus(ReservationFormStatus status) {
        this.status = status;
        return this;
    }

    public Employee getConfirmedBy() {
        return confirmedBy;
    }

    public ReservationForm setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationForm)) return false;
        ReservationForm that = (ReservationForm) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "ReservationForm{" +
            "id=" +
            id +
            ", customer_personalId=" +
            customer.getPersonalId() +
            ", createdAt=" +
            createdAt +
            ", personNum=" +
            personNum +
            ", otherRequirements='" +
            otherRequirements +
            '\'' +
            ", deposit=" +
            deposit +
            ", completeDepositDate=" +
            completeDepositDate +
            ", depositContent='" +
            depositContent +
            '\'' +
            ", checkInDate=" +
            checkInDate +
            ", checkOutDate=" +
            checkOutDate +
            ", finePercent=" +
            finePercent +
            ", status=" +
            status.getName() +
            ", confirmedBy=" +
            (confirmedBy != null ? confirmedBy.getAccount().getFullName() : "NULL") +
            '}'
        );
    }
}
