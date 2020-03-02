package com.devau.hotel.controller;

import com.devau.hotel.entity.RoomType;
import com.devau.hotel.service.base.RoomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/room-type")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping
    public List<RoomType> findAll() {
        return this.roomTypeService.findAll();

    }
    @PostMapping
    public RoomType save(@RequestBody RoomType roomType){
        this.roomTypeService.save(roomType);
        return roomType;

    }

}
