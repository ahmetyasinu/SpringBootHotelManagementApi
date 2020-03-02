package com.devau.hotel.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roomBooking")
public class RoomBooking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervationId")
    private Integer rezervationId;
    @Column(name="nightPoint")
    private Long nighPoint;
    @Column(name = "sumPrice")
    private Long sumPrice;
    @NotEmpty(message = "Boş bırakılamaz.")
    @Size(min=1, max=35)
    @Pattern(regexp = "[A-Za-zçÇğĞıIiİşŞöÖüÜ\\s]+",message = "35 Harf ile sınırlandırılmıştır ve Harf dışında bir karakter giremezsiniz.")
    @Column(name = "nameSurname")
    private String nameSurname;
    @NotEmpty
    @Length(min = 11,max = 11,message = "Tc 11 Basamaktan az değer girilemez")
    @Column(name = "tc")
    private String tc;
    @Email(message = "Emailinizi Uygun Formatlarda Giriniz.")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Boş bırakılamaz.")
    @Pattern(regexp ="[0-9\\s]{14}",message = "Telefon numarası bulunamadı.")
    @Column(name = "telNumber")
    private String telNumber;
    @NotEmpty(message = "Boş bırakılamaz.")
    @Column(name = "startDate")
    private String startDate;
    @NotEmpty(message = "Boş bırakılamaz.")
    @Column(name = "endDate")
    private String endDate;
    @Column(name = "roomTypeId")
    private Integer roomTypeId;

    @Column(name = "room_id")
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_number", insertable = false, updatable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "roomTypeId", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomType roomType;

    public RoomBooking() {
    }

    public Long getNighPoint() {
        return nighPoint;
    }

    public void setNighPoint(Long nighPoint) {
        this.nighPoint = nighPoint;
    }

    public Long getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Long sumPrice) {
        this.sumPrice = sumPrice;
    }
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }


    public Integer getRezervationId() {
        return rezervationId;
    }

    public void setRezervationId(Integer rezervationId) {
        this.rezervationId = rezervationId;
    }

    @Override
    public String toString() {
        return "RoomBooking{" +
                "nameSurname='" + nameSurname + '\'' +
                ", tc=" + tc +
                ", email='" + email + '\'' +
                ", telNumber=" + telNumber +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", roomId=" + roomId +
                ", rezervationId=" + rezervationId +
                '}';
    }
}
