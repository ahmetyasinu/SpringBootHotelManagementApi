package com.devau.hotel.service.base;

import com.devau.hotel.entity.Room;
import com.devau.hotel.entity.RoomBooking;

import java.util.List;

public interface RoomService {
    public List<Room> findAll();
    public Room findById(int id);
    public Room save(Room room);
    public void deleteById(int id);
    public void update(Room theRoom, int id);
    public List<Room> findAllByRoomType(Integer id);

}
