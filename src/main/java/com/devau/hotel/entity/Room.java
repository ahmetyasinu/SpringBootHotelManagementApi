package com.devau.hotel.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "room")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_number")
    private Integer roomNumber;

    @ManyToOne
    @JoinColumn(columnDefinition = "room_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomType roomType;

    @Column(name = "room_type_id")
    private Integer roomTypeId;



    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Room() {
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomType=" + roomType +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
