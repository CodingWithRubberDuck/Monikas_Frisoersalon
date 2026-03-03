package org.example.monikas_frisoersalon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.exceptions.ValidationException;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.models.Booking;
import org.example.monikas_frisoersalon.models.HairTreatment;
import org.example.monikas_frisoersalon.models.Hairdresser;
import org.example.monikas_frisoersalon.models.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class BookingController {

    private final BookingService service;
    private final Navigator navigator;
    private final ExceptionController exception;

    public BookingController(BookingService service, Navigator navigator, ExceptionController exception) {
        this.service = service;
        this.navigator = navigator;
        this.exception = exception;
    }

    /// comboBox
    @FXML
    private ComboBox<Hairdresser> comboBoxHairdresser;
    @FXML
    private ComboBox<HairTreatment> comboBoxHairTreatment;

    /// listView
    @FXML
    private ListView<HairTreatment> listViewHairTreatment;

    /// tableView + tableColumns
    @FXML
    private TableView<Booking> tableViewBooking;

    @FXML
    private TableColumn<Booking, Number> tableColumnBookingBookingId;
    @FXML
    private TableColumn<Booking, String> tableColumnBookingDate;
    @FXML
    private TableColumn<Booking, String> tableColumnBookingStartTime;
    @FXML
    private TableColumn<Booking, String> tableColumnBookingEndTime;
    @FXML
    private TableColumn<Booking, String> tableColumnBookingStatus;
    @FXML
    private TableColumn<Booking, Number> tableColumnBookingHairdresserId;
    @FXML
    private TableColumn<Booking, Number> tableColumnBookingCustomerId;

    /// datePicker
    @FXML
    private DatePicker datePickerBooking;

    /// textField
    @FXML
    private TextField textFieldBookingTime;

    @FXML
    private TextField textFieldBookingCustomerName;

    @FXML
    private TextField textFieldBookingPhoneNumber;



    /// checkBox
    @FXML
    private CheckBox checkBoxShowCancelledBookings;

    /// Opretter observable lists
    private final ObservableList<Hairdresser> hairdresserObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> hairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> chosenHairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<Booking> visibleBookingsObservableList = FXCollections.observableArrayList();


    //Formatter
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");


    @FXML
    public void initialize() {
        //Forbinder ObservableList med comboBox
        comboBoxHairdresser.setItems(hairdresserObservableList);
        comboBoxHairTreatment.setItems(hairTreatmentObservableList);

        //Henter data fra databasen og indsætter det i observableLists
        try {
            hairdresserObservableList.setAll(service.handleShowAllHairdressers());
            hairTreatmentObservableList.setAll(service.handlegetAllHairTreatments());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        //Standard Dato
        datePickerBooking.setValue(LocalDate.now());

        tableViewBooking.setItems(visibleBookingsObservableList);

        tableColumnBookingBookingId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getBookingId()));
        tableColumnBookingDate.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate().toString()));
        tableColumnBookingStartTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStartTime().format(timeFmt)));
        tableColumnBookingEndTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEndTime().format(timeFmt)));
        tableColumnBookingStatus.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus().name()));
        tableColumnBookingHairdresserId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getHairdresserId()));
        tableColumnBookingCustomerId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getCustomerId()));

        getBookingsByDate();

        listViewHairTreatment.setItems(chosenHairTreatmentObservableList);
    }


    //Tilføj HairTreatment til listview
    @FXML
    private void onClickAddTreatmentToList() {
        HairTreatment hT = comboBoxHairTreatment.getSelectionModel().getSelectedItem();
        chosenHairTreatmentObservableList.add(hT);
    }

    //Fjern HairTreatment til listView
    @FXML
    private void onClickRemoveTreatmentFromList() {
        HairTreatment hT = listViewHairTreatment.getSelectionModel().getSelectedItem();
        chosenHairTreatmentObservableList.remove(hT);
    }

    //Aflys Booking
    @FXML
    private void onClickCancelBooking() {
        Booking b = tableViewBooking.getSelectionModel().getSelectedItem();

        b.setStatus(Status.CANCELLED);

        try {
            service.handleUpdateBooking(b); //Opdaterer Booking i databasen
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        tableViewBooking.refresh();
    }

    @FXML
    private void onClickSetBookingToPending() {
        Booking b = tableViewBooking.getSelectionModel().getSelectedItem();

        b.setStatus(Status.PENDING);

        try {
            service.handleUpdateBooking(b); //Opdaterer Booking i databasen
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        tableViewBooking.refresh();
    }

    @FXML
    private void onCheckShowCancelledBookings() {
        List<Booking> allBookings;

        //Henter alle bookings fra databasen og ligger dem ind i en liste allBookings
        try {
            allBookings = service.handleGetBookingsByDate(datePickerBooking.getValue());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
            return;
        }

        //Hvis checkbox IKKE er checked vises CANCELLED Bookings ikke
        if (!checkBoxShowCancelledBookings.isSelected()) {
            allBookings.removeIf(b -> b.getStatus().equals(Status.CANCELLED));
        }

        /*
        List<Booking> filteredBookings = new ArrayList<>();

        for (Booking b : allBookings) {
            if (b.getStatus().equals(Status.CANCELLED)) {
                filteredBookings.add(b);
            }
        }

        allBookings = filteredBookings;
         */

        //Opdaterer listen
        visibleBookingsObservableList.setAll(allBookings);
    }

    @FXML
    private void datePickerSelectBookingDate() {
        getBookingsByDate();
    }

    @FXML
    private void onButtonClickAddBooking() {
        try {
            LocalDate date = datePickerBooking.getValue();
            if (date == null) {
                throw new IllegalArgumentException("Der skal indsættes en reel dato (YYYY-MM-DD), f.eks. " + LocalDate.now());
            }

            LocalTime suggestedTime;
            try {
                suggestedTime = LocalTime.parse(textFieldBookingTime.getText().trim(), timeFmt);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalArgumentException("Der skal indsættes et reelt tidspunkt (HH:mm), f.eks. 14:30");
            }

            List<HairTreatment> chosenTreatments = new ArrayList<>(chosenHairTreatmentObservableList);
            if (chosenTreatments.isEmpty()) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden mindst én behandling");
            }

            Hairdresser hairdresser = comboBoxHairdresser.getValue();
            if (hairdresser == null) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden en frisør");
            }


            String customerName = textFieldBookingCustomerName.getText().trim();
            if (customerName.isBlank()) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden et navn");
            }

            String phoneNumber = textFieldBookingPhoneNumber.getText().trim();
            if (phoneNumber.isBlank()) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden et telefonnummer");
            }

            Booking newBooking = new Booking(date, suggestedTime, hairdresser.getHairdresserId(), Status.PENDING);

            service.validateBooking(newBooking, chosenTreatments);

        } catch (IllegalArgumentException iae) {
            exception.showAlert("Vær Opmærksom På", iae.getMessage());
        } catch (ValidationException ve) {
            exception.showAlert("Valideringsfehl", ve.getMessage());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        } catch (Exception e) {
            exception.showAlert("Ukendt Fejl", "En ukendt fejl er sket i forbindelse med tilføjelse af booking");
        }
    }


    @FXML
    private void switchToLogin() {
        try {
            navigator.goTo("login-view.fxml", "Login");
        } catch (RuntimeException re) {
            exception.showAlert("Display Fejl", re.getMessage());
        }
    }

    private void getBookingsByDate() {
        try {
            List<Booking> all = service.handleGetBookingsByDate(datePickerBooking.getValue());
            visibleBookingsObservableList.setAll(all);
            onCheckShowCancelledBookings();
        } catch (DataAccessException dae) {
            exception.showAlert("Database Fejl", dae.getMessage());
        }
    }


}
