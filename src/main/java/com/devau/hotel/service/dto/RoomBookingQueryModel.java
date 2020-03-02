package com.devau.hotel.service.dto;

public class RoomBookingQueryModel {
    private String nameSurname;
    private String tc;
    private String email;

    public RoomBookingQueryModel() {
    }

    public RoomBookingQueryModel(String nameSurname, String tc, String email) {
        this.nameSurname = nameSurname;
        this.tc = tc;
        this.email = email;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
