package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository {
    List<Booking> findAll();

    List<Booking> findAllByDate(LocalDate date);
}
