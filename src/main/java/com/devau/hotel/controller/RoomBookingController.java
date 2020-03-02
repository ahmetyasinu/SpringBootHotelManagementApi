package com.devau.hotel.controller;

import com.devau.hotel.entity.RoomBooking;
import com.devau.hotel.repository.RoomTypeRepository;
import com.devau.hotel.service.base.RoomBookingService;
import com.devau.hotel.service.dto.RoomBookingQueryModel;
import com.devau.hotel.service.dto.RoomBookingReportQueryModel;
import com.devau.hotel.service.dto.RoomBookingTypeQueryModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class RoomBookingController {
    private final RoomBookingService roomBookingService;
    private final RoomTypeRepository roomTypeRepository;

    public RoomBookingController(RoomBookingService roomBookingService, RoomTypeRepository roomTypeRepository) {
        this.roomBookingService = roomBookingService;
        this.roomTypeRepository = roomTypeRepository;
    }
    @GetMapping
    public List<RoomBooking> findAll(){

        return this.roomBookingService.findAll();
    }
    @PostMapping
    public RoomBooking roomBooking(@RequestBody @Valid RoomBooking roomBooking) {

        return this.roomBookingService.save(roomBooking);
    }
    @GetMapping("/update/{reservationId}")
    public RoomBooking roomBooking(@PathVariable int reservationId) {

        RoomBooking roomBooking = roomBookingService.findById(reservationId);

        // throw exception if null

        if (roomBooking == null) {
            throw new RuntimeException("reservation not found - " + reservationId);
        }


        return roomBooking;
    }

    @GetMapping("/price/{startDate}/{endDate}/{roomTypeId}")
    public Double roomBooking(@PathVariable String startDate,@PathVariable String endDate,@PathVariable int roomTypeId){
        Double roomBooking1=roomBookingService.operation(startDate, endDate, roomTypeId);
        return roomBooking1;
    }
    @GetMapping("/searchField/{nameSurname}")
    public List<RoomBooking> roomBooking(@PathVariable String nameSurname){

        return this.roomBookingService.findByNameSurname(nameSurname);

    }

    @GetMapping("/searchField/spec/{nameSurname}")
    public List<RoomBooking> roomBookingWithSpec(@PathVariable String nameSurname){

        return this.roomBookingService.findByNameSurnameWithSpec(nameSurname);

    }

    @GetMapping("/test")
    public List<String> roomBooking(){
        return this.roomBookingService.jpaTest();
    }
    @GetMapping("/temel")
    public List<RoomBooking> findAllByRoomType(){

        return this.roomBookingService.jpaTestWithSpec();
    }


    @GetMapping("/test2")
    public List<RoomBookingQueryModel> roomBookingQueryModels(){
        return this.roomBookingService.jpaTest2();
    }

    @GetMapping("/test3")
    public List<RoomBookingQueryModel> jpaTestWithTuple(){
        return this.roomBookingService.jpaTestWithTuple();
    }
    @GetMapping("/test4")
    public List<RoomBookingReportQueryModel> jpaTestWithTuple1(){
        return this.roomBookingService.jpaTestWithTuple1();
    }
    @GetMapping("/report")
    public List<RoomBookingTypeQueryModel> jpaTestWithTuple2(){
        return this.roomBookingService.jpaTestWithTuple2();
    }
    @DeleteMapping("/delete/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {


        roomBookingService.deleteById(reservationId);

    }


}
