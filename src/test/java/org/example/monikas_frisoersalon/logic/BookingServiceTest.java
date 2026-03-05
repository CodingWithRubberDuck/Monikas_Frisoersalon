package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.BookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLBookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLPersonRepository;
import org.example.monikas_frisoersalon.dal.MySQLTreatmentRepository;
import org.example.monikas_frisoersalon.exceptions.ValidationException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.HairTreatment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingService service;

    @BeforeEach
    void setup() {
        DBConnection db = new DBConnection();
        service = new BookingService(new MySQLBookingRepository(db), new MySQLPersonRepository(db), new MySQLTreatmentRepository(db));
    }

    @Test
    void validateBooking_shouldRejectTooLateTime() {
        List<HairTreatment> treatments = new ArrayList<>();
        assertThrows(ValidationException.class, () -> service.validateBooking(LocalDate.now(), LocalTime.parse("21:30"), 21, treatments));
    }

    @Test
    void validateBooking_shouldRejectTooEarlyTime() {
        List<HairTreatment> treatments = new ArrayList<>();
        assertThrows(ValidationException.class, () -> service.validateBooking(LocalDate.now(), LocalTime.parse("05:30"), 21, treatments));
    }

    @Test
    void validateAddTreatment_shouldRejectDuplicate() {
        HairTreatment testTreatment = new HairTreatment(1, "test", 45);
        HairTreatment[] treatments = new HairTreatment[]{testTreatment};

        assertThrows(ValidationException.class, () -> service.validateAddTreatment(treatments, testTreatment));
    }
}