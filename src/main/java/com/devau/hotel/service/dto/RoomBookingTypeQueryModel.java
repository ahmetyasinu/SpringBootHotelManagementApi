package com.devau.hotel.service.dto;

public class RoomBookingTypeQueryModel {
    private Integer roomTypeId;
    private String type;
    private Long count;
    private Long sumPrice;


    public RoomBookingTypeQueryModel( Integer roomTypeId, String type, Long count,Long sumPrice) {
        this.type = type;
        this.count = count;
        this.sumPrice=sumPrice;
        this.roomTypeId = roomTypeId;
    }

    public Long getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Long sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

}
