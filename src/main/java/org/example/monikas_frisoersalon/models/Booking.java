package org.example.monikas_frisoersalon.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private int bookingId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int hairdresserId;
    private int customerId;
    private Status status;
    private String hairdresserName;
    private String customerName;

    public Booking(int bookingId, LocalDate date, LocalTime startTime, LocalTime endTime, int hairdresserId, int customerId, Status status){
        this.bookingId = bookingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hairdresserId = hairdresserId;
        this.customerId = customerId;
        this.status = status;
    }

    public Booking(int bookingId, LocalDate date, LocalTime startTime, LocalTime endTime, int hairdresserId, int customerId, Status status, String hairdresserName, String customerName){
        this.bookingId = bookingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hairdresserId = hairdresserId;
        this.customerId = customerId;
        this.status = status;
        this.hairdresserName = hairdresserName;
        this.customerName = customerName;
    }


    //Til tilføjelse af booking
    public Booking(LocalDate date, LocalTime startTime, int hairdresserId, Status status){
        this.date = date;
        this.startTime = startTime;
        this.hairdresserId = hairdresserId;
        this.status = status;
    }


    /// Getters
    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getHairdresserId() {
        return hairdresserId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Status getStatus() {
        return status;
    }


    /// Setters
    public void setStatus(Status status) {
        this.status = status;
    }


    public void setEndTime(LocalTime newEndTime){
        this.endTime = newEndTime;
    }



}
