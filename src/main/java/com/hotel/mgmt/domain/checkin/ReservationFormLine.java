package com.hotel.mgmt.domain.checkin;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.room.Suite;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;

@Embeddable
class ReservationFormLineId implements Serializable {

    private static final long serialVersionUID = 6246999007575555123L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_PHIEU_DAT", nullable = false)
    private ReservationForm reservationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_HANG_PHONG", nullable = false)
    private Suite suite;

    public ReservationFormLineId() {}

    public ReservationFormLineId(ReservationForm reservationForm, Suite suite) {
        this.reservationForm = reservationForm;
        this.suite = suite;
    }

    public ReservationForm getCheckInForm() {
        return reservationForm;
    }

    public Suite getSuite() {
        return suite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationFormLineId that = (ReservationFormLineId) o;
        return Objects.equals(reservationForm, that.reservationForm) && Objects.equals(suite, that.suite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationForm, suite);
    }

    @Override
    public String toString() {
        return "ReservationFormLineId{" + "checkInFormId=" + reservationForm.getId() + ", suiteId=" + suite.getId() + '}';
    }
}

@Entity
@Table(name = "CT_PHIEU_DAT")
public class ReservationFormLine implements Serializable {

    private static final long serialVersionUID = 1870185518410354750L;

    @EmbeddedId
    private ReservationFormLineId id;

    @Min(0)
    @Column(name = "SO_LUONG_PHONG")
    private int roomNum;

    @Min(0)
    @Column(name = "GIA_HANG_PHONG")
    private long suitePrice;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    @PercentConstaint
    @Column(name = "PHAN_TRAM_GIAM")
    private int discountPercent;

    public ReservationFormLine() {}

    public ReservationFormLine(ReservationForm reservationForm, Suite suite) {
        this.id = new ReservationFormLineId(reservationForm, suite);
    }

    public ReservationFormLineId getId() {
        return id;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public long getSuitePrice() {
        return suitePrice;
    }

    public void setSuitePrice(long suitePrice) {
        this.suitePrice = suitePrice;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationFormLine)) return false;
        ReservationFormLine that = (ReservationFormLine) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "ReservationFormLine{" +
            "id=" +
            id +
            ", roomNum=" +
            roomNum +
            ", suitePrice=" +
            suitePrice +
            ", vat=" +
            vat +
            ", discountPercent=" +
            discountPercent +
            '}'
        );
    }
}
