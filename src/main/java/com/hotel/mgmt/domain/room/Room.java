package com.hotel.mgmt.domain.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "PHONG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room implements Serializable {

    private static final long serialVersionUID = 2661215691678077568L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ_PHONG")
    @SequenceGenerator(name = "ID_SEQ_PHONG", allocationSize = 1, initialValue = 1, sequenceName = "ID_SEQ_PHONG")
    @Column(name = "MA_PHONG")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_TRANG_THAI_PHONG", nullable = false)
    @JsonBackReference
    private RoomStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MA_HANG_PHONG", nullable = false)
    @JsonBackReference
    private Suite suite;

    @Min(1)
    @Column(name = "VI_TRI_TANG")
    private int floor;

    public Room() {}

    public Integer getId() {
        return id;
    }

    public Room setId(Integer id) {
        this.id = id;
        return this;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public Room setStatus(RoomStatus status) {
        this.status = status;
        return this;
    }

    public Suite getSuite() {
        return suite;
    }

    public Room setSuite(Suite suite) {
        this.suite = suite;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public Room setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room that = (Room) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", status=" + status.getName() + ", suite=" + suite + ", floor=" + floor + '}';
    }
}
