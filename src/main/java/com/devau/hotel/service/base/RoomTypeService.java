package com.devau.hotel.service.base;


import com.devau.hotel.entity.RoomType;

import java.util.List;

public interface RoomTypeService {
    public List<RoomType> findAll();
    public RoomType findById(int id);
    public void save(RoomType room);
    public void deleteById(int id);
    public void update(RoomType theRoomType, int id);
}
