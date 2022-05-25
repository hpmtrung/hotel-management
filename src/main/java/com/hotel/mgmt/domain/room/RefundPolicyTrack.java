package com.hotel.mgmt.domain.room;

import com.hotel.mgmt.domain.Employee;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable
class RefundPolicyTrackId implements Serializable {

    private static final long serialVersionUID = -4713432791058767757L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_CSHH")
    private RefundPolicy refundPolicy;

    @NotNull
    @Column(name = "NGAYGIO_AP_DUNG")
    private Instant editedBy;

    public RefundPolicyTrackId() {}

    public RefundPolicyTrackId(RefundPolicy refundPolicy, Instant editedBy) {
        this.refundPolicy = refundPolicy;
        this.editedBy = editedBy;
    }

    public RefundPolicy getRefundPolicy() {
        return refundPolicy;
    }

    public Instant getEditedBy() {
        return editedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefundPolicyTrackId that = (RefundPolicyTrackId) o;
        return Objects.equals(refundPolicy, that.refundPolicy) && Objects.equals(editedBy, that.editedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refundPolicy, editedBy);
    }

    @Override
    public String toString() {
        return "RefundPolicyTrackId{" + "refundPolicyName=" + refundPolicy.getName() + ", editedBy=" + editedBy + '}';
    }
}

@Entity
@Table(name = "CT_HOAN_HUY")
public class RefundPolicyTrack implements Serializable {

    private static final long serialVersionUID = 4824313283309972238L;

    @EmbeddedId
    private RefundPolicyTrackId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nv", nullable = false)
    private Employee editedBy;

    public RefundPolicyTrack() {}

    public RefundPolicyTrackId getId() {
        return id;
    }

    public Employee getEditedBy() {
        return editedBy;
    }

    public RefundPolicyTrack withId(RefundPolicyTrackId id) {
        this.id = id;
        return this;
    }

    public RefundPolicyTrack withEditedBy(Employee editedBy) {
        this.editedBy = editedBy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefundPolicyTrack)) return false;
        RefundPolicyTrack that = (RefundPolicyTrack) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "RefundPolicyTrack{" + "id=" + id + ", editedBy=" + editedBy.getAccount().getFullName() + '}';
    }
}
