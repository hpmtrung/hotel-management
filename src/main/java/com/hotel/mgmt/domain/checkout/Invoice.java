package com.hotel.mgmt.domain.checkout;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hotel.mgmt.domain.Employee;
import com.hotel.mgmt.domain.checkin.RentalFormLine;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "HOA_DON")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = -7002783174933174375L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_HOA_DON")
    @SequenceGenerator(name = "ID_SEQ_HOA_DON", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_HOA_DON")
    @Column(name = "MA_HOA_DON")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "HOA_DON_CT_PHIEU_THUE",
        joinColumns = @JoinColumn(name = "MA_CT_PHIEU_THUE"),
        inverseJoinColumns = @JoinColumn(name = "MA_HOA_DON")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RentalFormLine> rentalFormLines = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV_LAP", nullable = false)
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_PHUONG_THUC_TT", nullable = false)
    private PayMethod payMethod;

    @NotNull
    @Column(name = "NGAYGIO_LAP")
    private Instant createdAt;

    @Min(0)
    @Column(name = "SO_TIEN_GIAM")
    private long discount;

    @Min(0)
    @Column(name = "TONG_TIEN")
    private long total;

    @Size(min = 13, max = 13)
    @Column(name = "MASOTHUE")
    private String taxCode;

    public Invoice() {}

    public Long getId() {
        return id;
    }

    public Invoice setId(Long id) {
        this.id = id;
        return this;
    }

    public Set<RentalFormLine> getRentalFormLines() {
        return rentalFormLines;
    }

    public Invoice setRentalFormLines(Set<RentalFormLine> rentalFormLines) {
        this.rentalFormLines = rentalFormLines;
        return this;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public Invoice setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public Invoice setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Invoice setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public long getDiscount() {
        return discount;
    }

    public Invoice setDiscount(long discount) {
        this.discount = discount;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Invoice setTotal(long total) {
        this.total = total;
        return this;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Invoice setTaxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return id != null && Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Invoice{" +
            "id=" +
            id +
            ", createdById=" +
            createdBy.getId() +
            ", payMethod=" +
            payMethod +
            ", createdAt=" +
            createdAt +
            ", discount=" +
            discount +
            ", total=" +
            total +
            ", taxCode='" +
            taxCode +
            '\'' +
            '}'
        );
    }
}
