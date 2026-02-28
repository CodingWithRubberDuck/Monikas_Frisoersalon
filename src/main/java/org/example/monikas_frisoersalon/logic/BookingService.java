package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.BookingRepository;
import org.example.monikas_frisoersalon.dal.PersonRepository;
import org.example.monikas_frisoersalon.dal.TreatmentRepository;
import org.example.monikas_frisoersalon.models.Hairdresser;

import java.util.List;

public class BookingService {

    private final BookingRepository bookingRepo;
    private final PersonRepository personRepo;
    private final TreatmentRepository treatmentRepo;

    public BookingService(BookingRepository bookingRepo, PersonRepository personRepo, TreatmentRepository treatmentRepo){
        this.bookingRepo = bookingRepo;
        this.personRepo = personRepo;
        this.treatmentRepo = treatmentRepo;
    }

    public List<Hairdresser> handleShowAllHairdressers() {
        return personRepo.showAllHairdressers();
    }

}
