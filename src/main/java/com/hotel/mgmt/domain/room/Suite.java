package com.hotel.mgmt.domain.room;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "HANG_PHONG")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Suite implements Serializable {

    private static final long serialVersionUID = -174376290777802935L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_HANG_PHONG")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_LOAI_PHONG", nullable = false)
    @JsonBackReference
    private SuiteType suiteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_KIEU_PHONG", nullable = false)
    @JsonBackReference
    private SuiteStyle suiteStyle;

    @Min(0)
    @Column(name = "DIEN_TICH_PHONG")
    private long area;

    @Min(0)
    @Column(name = "GIA_HANG_PHONG")
    private long price;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_CSHH", nullable = false)
    private RefundPolicy refundPolicy;

    @OneToMany(mappedBy = "suite", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "suite", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private List<SuiteImage> images = new ArrayList<>();

    public Suite() {}

    public int getId() {
        return id;
    }

    public Suite setId(int id) {
        this.id = id;
        return this;
    }

    public SuiteType getSuiteType() {
        return suiteType;
    }

    public Suite setSuiteType(SuiteType suiteType) {
        this.suiteType = suiteType;
        return this;
    }

    public SuiteStyle getSuiteStyle() {
        return suiteStyle;
    }

    public Suite setSuiteStyle(SuiteStyle suiteStyle) {
        this.suiteStyle = suiteStyle;
        return this;
    }

    public long getArea() {
        return area;
    }

    public Suite setArea(long area) {
        this.area = area;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Suite setPrice(long price) {
        this.price = price;
        return this;
    }

    public int getVat() {
        return vat;
    }

    public Suite setVat(int vat) {
        this.vat = vat;
        return this;
    }

    public RefundPolicy getRefundPolicy() {
        return refundPolicy;
    }

    public Suite setRefundPolicy(RefundPolicy refundPolicy) {
        this.refundPolicy = refundPolicy;
        return this;
    }

    public List<SuiteImage> getImages() {
        return images;
    }

    public Suite setImages(List<SuiteImage> images) {
        this.images = images;
        return this;
    }

    public Suite setRooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public void addImage(SuiteImage image) {
        images.add(image);
        image.setSuite(this);
    }

    public void removeImage(SuiteImage image) {
        images.remove(image);
        // image.setSuite(null);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setSuite(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setSuite(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suite suite = (Suite) o;
        return suiteType.equals(suite.suiteType) && suiteStyle.equals(suite.suiteStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suiteType, suiteStyle);
    }

    @Override
    public String toString() {
        return (
            "Suite{" +
            "id=" +
            id +
            ", suiteTypeName=" +
            suiteType.getName() +
            ", suiteStyleName=" +
            suiteStyle.getName() +
            ", area=" +
            area +
            ", price=" +
            price +
            ", vat=" +
            vat +
            '}'
        );
    }
}
