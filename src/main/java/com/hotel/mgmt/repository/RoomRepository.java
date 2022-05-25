package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.room.Room;
import com.hotel.mgmt.domain.room.RoomStatus;
import com.hotel.mgmt.domain.room.Suite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findBySuite(Suite suite);

    List<Room> findByStatus(RoomStatus status);

    List<Room> findByFloor(int floor);
}
