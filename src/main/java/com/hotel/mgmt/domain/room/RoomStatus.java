package com.hotel.mgmt.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "TRANG_THAI_PHONG")
@Immutable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RoomStatus implements Serializable {

    private static final long serialVersionUID = 3770598023442050170L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_TRANG_THAI_PHONG")
    private Integer id;

    @NotBlank
    @NaturalId
    @Size(max = 50)
    @Column(name = "TEN", unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "status", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonManagedReference
    private Set<Room> rooms = new HashSet<>();

    public RoomStatus() {}

    public Integer getId() {
        return id;
    }

    public RoomStatus setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomStatus setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public RoomStatus setRooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomStatus that = (RoomStatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "RoomStatus{" + "id=" + id + ", name='" + name + '}';
    }
}
