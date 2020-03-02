package com.devau.hotel.repository;

import com.devau.hotel.entity.Room;
import com.devau.hotel.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer>, JpaSpecificationExecutor<RoomBooking> {

 /*   @Query("select a from RoomBooking a where a.roomId = :roomNumber and ((:startDate BETWEEN a.startDate and a.endDate) or (:endDate BETWEEN a.startDate and a.endDate)) or((a.startDate between :startDate and :endDate)or(a.endDate between :startDate and :endDate)) ")
    List<RoomBooking> findAvaibleRoom(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("roomNumber") int roomNumber);*/

    /*@Query("select a from RoomBooking a where a.nameSurname like concat('%',UPPER(:nameSurname),'%') ")
    List<RoomBooking> findByNameSurname(@Param("nameSurname") String nameSurname);*/




}
