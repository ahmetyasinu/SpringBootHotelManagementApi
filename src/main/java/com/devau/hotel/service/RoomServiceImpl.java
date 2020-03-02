package com.devau.hotel.service;

import com.devau.hotel.repository.RoomRepository;
import com.devau.hotel.entity.Room;
import com.devau.hotel.service.base.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findAll() {
        return this.roomRepository.findAll();
    }

    @Override
    public Room findById(int id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Böyle bir id bulunamadı."));
    }

    @Transactional
    @Override
    public Room save(Room room) {
        return this.roomRepository.save(room);
    }

    @Override
    public void deleteById(int id) {
        this.roomRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(Room theRoom, int id) {
        if (theRoom.getRoomNumber() != id) {
            throw new RuntimeException("Id ler eşleşmedi.");
        }
        this.roomRepository.save(theRoom);
    }

    @Override
    public List<Room> findAllByRoomType(Integer roomTypeId) {
        return this.roomRepository.findAllByRoomType(roomTypeId);
    }
}
