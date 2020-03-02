package com.devau.hotel.controller;

import com.devau.hotel.entity.Room;
import com.devau.hotel.entity.RoomBooking;
import com.devau.hotel.service.base.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public List<Room> listRoom() {
        // get employees from db
        List<Room> theRoom = roomService.findAll();
        return theRoom;
    }

    @PostMapping
    public Room addRoom(@RequestBody Room theRoom) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        return roomService.save(theRoom);
    }

    @GetMapping("/type/{roomType}")
    public List<Room> findAllByRoomType(@PathVariable("roomType") Integer roomTypeId){

        return this.roomService.findAllByRoomType(roomTypeId);
    }




}
