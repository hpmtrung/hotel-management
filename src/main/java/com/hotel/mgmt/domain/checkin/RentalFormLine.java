package com.hotel.mgmt.domain.checkin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hotel.mgmt.annotations.constraints.percent.PercentConstaint;
import com.hotel.mgmt.domain.Customer;
import com.hotel.mgmt.domain.room.Room;
import com.hotel.mgmt.domain.service.ServiceCustomerUsage;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CT_PHIEU_THUE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RentalFormLine implements Serializable {

    private static final long serialVersionUID = -4680414281040813928L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_CT_PHIEU_THUE")
    @SequenceGenerator(name = "ID_SEQ_CT_PHIEU_THUE", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_CT_PHIEU_THUE")
    @Column(name = "MA_CT_PHIEU_THUE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_PHONG", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_PHIEU_THUE", nullable = false)
    @JsonBackReference
    private RentalForm rentalForm;

    @Min(0)
    @Column(name = "GIA_PHONG")
    private long roomPrice;

    @NotNull
    @Column(name = "NGAYGIO_CHECKIN")
    private Instant checkInDateTime;

    @NotNull
    @Column(name = "NGAYGIO_CHECKOUT")
    private Instant checkOutDateTime;

    @Min(0)
    @Column(name = "SO_TIEN_GIAM")
    private long discount;

    @Min(0)
    @Column(name = "TIEN_PHU_THU")
    private long extraPayment;

    @Size(max = 200)
    @Column(name = "GHI_CHU")
    private String note;

    @Min(0)
    @Column(name = "THANH_TIEN")
    private long total;

    @PercentConstaint
    @Column(name = "VAT")
    private int vat;

    @Column(name = "DA_THANH_TOAN")
    private boolean isPaid;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "KHACH_PHONG", joinColumns = @JoinColumn(name = "MA_CT_PHIEU_THUE"), inverseJoinColumns = @JoinColumn(name = "CMND"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private Set<Customer> customersInRoom = new HashSet<>();

    @OneToMany(mappedBy = "rentalFormLine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private List<ServiceCustomerUsage> serviceUsages = new ArrayList<>();

    public RentalFormLine() {}

    public Set<Customer> getCustomersInRoom() {
        return customersInRoom;
    }

    public RentalFormLine setCustomersInRoom(Set<Customer> personsInRoom) {
        this.customersInRoom = personsInRoom;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RentalFormLine setId(Long id) {
        this.id = id;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public RentalFormLine setRoom(Room room) {
        this.room = room;
        return this;
    }

    public RentalForm getRentalForm() {
        return rentalForm;
    }

    public RentalFormLine setRentalForm(RentalForm rentalForm) {
        this.rentalForm = rentalForm;
        return this;
    }

    public long getRoomPrice() {
        return roomPrice;
    }

    public RentalFormLine setRoomPrice(long roomPrice) {
        this.roomPrice = roomPrice;
        return this;
    }

    public Instant getCheckInDateTime() {
        return checkInDateTime;
    }

    public RentalFormLine setCheckInDateTime(Instant checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
        return this;
    }

    public Instant getCheckOutDateTime() {
        return checkOutDateTime;
    }

    public RentalFormLine setCheckOutDateTime(Instant checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
        return this;
    }

    public long getDiscount() {
        return discount;
    }

    public RentalFormLine setDiscount(long discount) {
        this.discount = discount;
        return this;
    }

    public long getExtraPayment() {
        return extraPayment;
    }

    public RentalFormLine setExtraPayment(long extraPayment) {
        this.extraPayment = extraPayment;
        return this;
    }

    public String getNote() {
        return note;
    }

    public RentalFormLine setNote(String note) {
        this.note = note;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public RentalFormLine setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getVat() {
        return vat;
    }

    public RentalFormLine setVat(int vat) {
        this.vat = vat;
        return this;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public RentalFormLine setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public List<ServiceCustomerUsage> getServiceUsages() {
        return serviceUsages;
    }

    public RentalFormLine setServiceUsages(List<ServiceCustomerUsage> serviceUsages) {
        this.serviceUsages = serviceUsages;
        return this;
    }

    public void addCustomerInRoom(Customer customer) {
        customersInRoom.add(customer);
    }

    public void removeCustomerInRoom(Customer customer) {
        customersInRoom.remove(customer);
    }

    public void addServiceUsage(ServiceCustomerUsage serviceUsage) {
        serviceUsages.add(serviceUsage);
        serviceUsage.setRentalFormLine(this);
    }

    public void removeServiceUsage(ServiceCustomerUsage serviceUsage) {
        serviceUsages.remove(serviceUsage);
        // serviceUsage.setRentalFormLine(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalFormLine)) return false;
        RentalFormLine that = (RentalFormLine) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RentalFormLine{" +
            "id=" +
            id +
            ", roomId=" +
            room.getId() +
            ", rentalFormId=" +
            rentalForm.getId() +
            ", roomPrice=" +
            roomPrice +
            ", checkInDateTime=" +
            checkInDateTime +
            ", checkOutDateTime=" +
            checkOutDateTime +
            ", discount=" +
            discount +
            ", extraPayment=" +
            extraPayment +
            ", note='" +
            note +
            '\'' +
            ", total=" +
            total +
            ", vat=" +
            vat +
            ", isPaid=" +
            isPaid +
            '}'
        );
    }
}
