package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.BookingRepository;
import org.example.monikas_frisoersalon.dal.LoginRepository;

public class BookingService {

    private final LoginRepository loginRepo;
    private final BookingRepository bookingRepo;

    public BookingService(LoginRepository loginRepo, BookingRepository bookingRepo){
        this.loginRepo = loginRepo;
        this.bookingRepo = bookingRepo;
    }
}
