package com.devau.hotel.repository;

import com.devau.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("select room from Room room where room.roomTypeId = :roomType ")
    List<Room> findAllByRoomType(@Param("roomType") Integer roomType);


}
