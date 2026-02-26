package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.BookingRepository;

public class BookingService {

    private final BookingRepository bookingRepo;

    public BookingService(BookingRepository bookingRepo){
        this.bookingRepo = bookingRepo;
    }


}
