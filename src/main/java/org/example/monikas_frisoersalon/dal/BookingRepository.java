package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Booking;

import java.util.List;

public interface BookingRepository {
    List<Booking> findAll();
}
