package com.devau.hotel.service.base;


import com.devau.hotel.entity.RoomBooking;
import com.devau.hotel.service.dto.RoomBookingQueryModel;
import com.devau.hotel.service.dto.RoomBookingReportQueryModel;
import com.devau.hotel.service.dto.RoomBookingTypeQueryModel;

import java.util.List;

public interface RoomBookingService {
    List<RoomBooking> findAll();

    RoomBooking findById(Integer id);

    RoomBooking save(RoomBooking roomBooking);

    void deleteById(Integer reservationId);

    void update(RoomBooking roomBooking, Integer id);

    Double operation(String startDate, String endDate, Integer roomTypeId);

    boolean findAvaibleRoom(String startDate, String endDate, Integer roomNumber);

    List<RoomBooking> findByNameSurname(String nameSurname);
    List<RoomBooking> findByNameSurnameWithSpec(String nameSurname);

    List<String> jpaTest();
    List<RoomBooking> jpaTestWithSpec();

    List<RoomBookingQueryModel> jpaTest2();
    List<RoomBookingQueryModel> jpaTestWithTuple();
    List<RoomBookingReportQueryModel> jpaTestWithTuple1();
    List<RoomBookingTypeQueryModel> jpaTestWithTuple2();
}
