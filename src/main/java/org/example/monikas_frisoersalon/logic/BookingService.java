package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.BookingRepository;
import org.example.monikas_frisoersalon.dal.PersonRepository;
import org.example.monikas_frisoersalon.dal.TreatmentRepository;
import org.example.monikas_frisoersalon.exceptions.ValidationException;
import org.example.monikas_frisoersalon.models.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class BookingService {

    private final BookingRepository bookingRepo;
    private final PersonRepository personRepo;
    private final TreatmentRepository treatmentRepo;

    public BookingService(BookingRepository bookingRepo, PersonRepository personRepo, TreatmentRepository treatmentRepo) {
        this.bookingRepo = bookingRepo;
        this.personRepo = personRepo;
        this.treatmentRepo = treatmentRepo;
    }

    public List<Hairdresser> handleShowAllHairdressers() {
        return personRepo.showAllHairdressers();
    }

    public List<HairTreatment> handleGetAllHairTreatments() {
        return treatmentRepo.getAllHairTreatments();
    }


    public List<Booking> handleGetBookingsByDate(LocalDate date, boolean showCancelled) {
        return bookingRepo.findAllByDate(date, showCancelled);
    }

    public Booking handleUpdateBooking(Booking booking) {
        return bookingRepo.updateBooking(booking);
    }

    public void validateAddTreatment(HairTreatment[] treatments, HairTreatment newTreatment) {
        for (HairTreatment treatment : treatments) {
            if (treatment == newTreatment) {
                throw new ValidationException("Man kan ikke tilføje den samme behandling flere gange til samme booking");
            }
        }
    }

    public LocalTime validateBooking(LocalDate date, LocalTime suggestedTime, int hairdresserId , List<HairTreatment> hairTreatments) {
        LocalTime suggestedEndTime = validateTimeWithinBaseInterval(suggestedTime, hairTreatments);

        List<Booking> possiblyConflictingBookings = bookingRepo.findSpecificBookings(date, hairdresserId);

        for (Booking booking : possiblyConflictingBookings) {
            LocalTime currentStart = booking.getStartTime();
            LocalTime currentEnd = booking.getEndTime();

            if (!(suggestedEndTime.compareTo(currentStart) < 1 || suggestedTime.compareTo(currentEnd) > -1)) {
                throw new ValidationException("Der kan desværre ikke indsættes en tid fra " + suggestedTime + "-" + suggestedEndTime +
                        "\nda det konflikter med tiden " + currentStart + "-" + currentEnd);
            }
        }

        return suggestedEndTime;
    }


    public int handlePersonExists(String name, String phoneNumber) {
        Optional<Customer> person = personRepo.existsInCustomer(name, phoneNumber);
        if (person.isPresent()) {
            return person.get().getCustomerId();
        } else {
            return -1;
        }
    }

    public int handleAddPerson(String name, String phoneNumber) {
        return personRepo.addPerson(name, phoneNumber);
    }

    public int handleAddCustomer(int personId) {
        return personRepo.addCustomer(personId);
    }

    public void handleAddBooking(LocalDate date, LocalTime suggestedStartTime, LocalTime suggestedEndTime, int hairdresserId, int customerId, List<HairTreatment> treatments) {
        int generatedId = bookingRepo.addBooking(date, suggestedStartTime, suggestedEndTime, hairdresserId, customerId);

        for (HairTreatment treatment : treatments) {
            bookingRepo.addBookingTreatments(treatment.getId(), generatedId);
        }
    }


    public List<HairTreatment> handleGetSpecificTreatments(int bookingId){
        return treatmentRepo.getSpecificTreatments(bookingId);
    }


    private LocalTime validateTimeWithinBaseInterval(LocalTime suggestedTime, List<HairTreatment> hairTreatments) {
        int duration = 0;
        for (HairTreatment hairTreatment : hairTreatments) {
            duration += hairTreatment.getDuration();
        }

        LocalTime suggestedEndTime = suggestedTime.plusMinutes(duration);

        final LocalTime EARLIEST_ALLOWED = LocalTime.parse("08:00");
        final LocalTime LATEST_ALLOWED = LocalTime.parse("17:00");
        if (suggestedTime.isBefore(EARLIEST_ALLOWED)) {
            throw new ValidationException("Der kan desværre ikke indsættes en booking før " + EARLIEST_ALLOWED);
        }
        if (suggestedEndTime.isAfter(LATEST_ALLOWED)) {
            throw new ValidationException("Der kan desværre ikke indsættes en booking efter " + LATEST_ALLOWED);
        }
        return suggestedEndTime;
    }
}
