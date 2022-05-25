package com.hotel.mgmt.domain.checkout;

import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.room.Suite;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
class PromotionSuiteId implements Serializable {

    private static final long serialVersionUID = 5575575545960737836L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_KHUYEN_MAI", nullable = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_HANG_PHONG", nullable = false)
    private Suite suite;

    public PromotionSuiteId() {}

    public PromotionSuiteId(Promotion promotion, Suite suite) {
        this.promotion = promotion;
        this.suite = suite;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Suite getSuite() {
        return suite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionSuiteId that = (PromotionSuiteId) o;
        return promotion.equals(that.promotion) && suite.equals(that.suite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotion, suite);
    }

    @Override
    public String toString() {
        return "PromotionSuiteId{" + "promotionId=" + promotion.getId() + ", suiteId=" + suite.getId() + '}';
    }
}

@Entity
@Table(name = "KHUYEN_MAI_HANG_PHONG")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PromotionSuite implements Serializable {

    private static final long serialVersionUID = 2665194249599619680L;

    @EmbeddedId
    private PromotionSuiteId id;

    @PercentConstaint
    @Column(name = "PHAN_TRAM_GIAM")
    private int discountPercent;

    @Size(max = 200)
    @Column(name = "GHI_CHU")
    private String note;

    public PromotionSuite() {}

    public PromotionSuite(Promotion promotion, Suite suite) {
        this.id = new PromotionSuiteId(promotion, suite);
    }

    public PromotionSuiteId getId() {
        return id;
    }

    public PromotionSuite setId(PromotionSuiteId id) {
        this.id = id;
        return this;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public PromotionSuite setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public String getNote() {
        return note;
    }

    public PromotionSuite setNote(String note) {
        this.note = note;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PromotionSuite)) return false;
        PromotionSuite that = (PromotionSuite) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PromotionSuite{" + "id=" + id + ", discountPercent=" + discountPercent + ", note='" + note + '\'' + '}';
    }
}
