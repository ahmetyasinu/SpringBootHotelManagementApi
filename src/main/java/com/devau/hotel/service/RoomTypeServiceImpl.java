package com.devau.hotel.service;

import com.devau.hotel.repository.RoomTypeRepository;
import com.devau.hotel.entity.RoomType;
import com.devau.hotel.service.base.RoomTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public List<RoomType> findAll() {
        return this.roomTypeRepository.findAll();
    }

    @Override
    public RoomType findById(int id) {
        return this.roomTypeRepository.findById(id).orElseThrow((() -> new RuntimeException("Böyle bir id bulunamadı.")));
    }

    @Transactional
    @Override
    public void save(RoomType room) {
        this.roomTypeRepository.save(room);
    }

    @Override
    public void deleteById(int id) {
        this.roomTypeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(RoomType theRoomType, int id) {
        if (theRoomType.getId() != id) {
            throw new RuntimeException("id ler eşleşmedi.");
        }
        this.roomTypeRepository.save(theRoomType);
    }
}
