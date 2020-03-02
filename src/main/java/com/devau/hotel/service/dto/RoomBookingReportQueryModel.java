package com.devau.hotel.service.dto;

public class RoomBookingReportQueryModel {
    private String nameSurname;
    private String type;

    public RoomBookingReportQueryModel() {
    }

    public RoomBookingReportQueryModel(String nameSurname, String type) {
        this.nameSurname = nameSurname;
        this.type = type;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
