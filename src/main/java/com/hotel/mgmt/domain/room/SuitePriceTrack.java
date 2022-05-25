package com.hotel.mgmt.domain.room;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.Employee;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Embeddable
class SuitePriceTrackId implements Serializable {

    private static final long serialVersionUID = 6974029470657442535L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_HANG_PHONG")
    private Suite suite;

    @NotNull
    @Column(name = "NGAYGIO_AP_DUNG")
    private Instant editedAt;

    public SuitePriceTrackId() {}

    public SuitePriceTrackId(Suite suite, Instant editedAt) {
        this.suite = suite;
        this.editedAt = editedAt;
    }

    public Suite getSuite() {
        return suite;
    }

    public Instant getEditedAt() {
        return editedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuitePriceTrackId that = (SuitePriceTrackId) o;
        return suite.equals(that.suite) && editedAt.equals(that.editedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suite, editedAt);
    }

    @Override
    public String toString() {
        return "SuitePriceTrackId{" + "suiteId=" + suite.getId() + ", editedAt=" + editedAt + '}';
    }
}

@Entity
@Table(name = "BANG_GIA_HANG_PHONG")
public class SuitePriceTrack implements Serializable {

    private static final long serialVersionUID = 2702892399235757248L;

    @EmbeddedId
    private SuitePriceTrackId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_NV", nullable = false)
    private Employee editedBy;

    @Min(0)
    @Column(name = "GIA")
    private long price;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    public SuitePriceTrack() {}

    public SuitePriceTrackId getId() {
        return id;
    }

    public Employee getEditedBy() {
        return editedBy;
    }

    public long getPrice() {
        return price;
    }

    public int getVat() {
        return vat;
    }

    public SuitePriceTrack withId(SuitePriceTrackId id) {
        this.id = id;
        return this;
    }

    public SuitePriceTrack withEditedBy(Employee editedBy) {
        this.editedBy = editedBy;
        return this;
    }

    public SuitePriceTrack withPrice(long price) {
        this.price = price;
        return this;
    }

    public SuitePriceTrack withVat(int vat) {
        this.vat = vat;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuitePriceTrack)) return false;
        SuitePriceTrack that = (SuitePriceTrack) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "SuitePriceTrack{" +
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
