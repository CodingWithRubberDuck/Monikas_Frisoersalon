package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Booking;
import org.example.monikas_frisoersalon.models.HairTreatment;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository {
    List<Booking> findAll();

    List<Booking> findAllByDate(LocalDate date, boolean showCancelled);

    Booking updateBooking(Booking booking);

    List<Booking> findSpecificBookings(LocalDate date, int hairdresserId);

    int addBooking(Booking newBooking);

    void addBookingTreatments(int treatmentId, int bookingId);
}
