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
    private final ExceptionHandler exception;

    //Formatter tid
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

    /// Opretter observable lists
    private final ObservableList<Hairdresser> hairdresserObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> hairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> chosenHairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<Booking> visibleBookingsObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> specificBookingTreatmentsObservableList = FXCollections.observableArrayList();

    public BookingController(BookingService service, Navigator navigator, ExceptionHandler exception) {
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
    //Booking
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
    private TableColumn<Booking, String> tableColumnBookingHairdresserName;
    @FXML
    private TableColumn<Booking, Number> tableColumnBookingCustomerId;


    //Treatment
    @FXML
    private TableView<HairTreatment> tableViewSpecificTreatments;

    @FXML
    private TableColumn<HairTreatment, Number> tableColumnTreatmentTreatmentId;
    @FXML
    private TableColumn<HairTreatment, String> tableColumnTreatmentName;
    @FXML
    private TableColumn<HairTreatment, Number> tableColumnTreatmentDuration;


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

    @FXML
    public void initialize() {
        //Forbinder ObservableList med comboBox
        comboBoxHairdresser.setItems(hairdresserObservableList);
        comboBoxHairTreatment.setItems(hairTreatmentObservableList);

        //Henter data fra databasen og indsætter det i observableLists
        try {
            hairdresserObservableList.setAll(service.handleShowAllHairdressers());
            hairTreatmentObservableList.setAll(service.handleGetAllHairTreatments());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        //Standard Dato
        datePickerBooking.setValue(LocalDate.now());

        refreshBookingTable();

        tableViewBooking.setItems(visibleBookingsObservableList);

        tableColumnBookingBookingId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getBookingId()));
        tableColumnBookingDate.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate().toString()));
        tableColumnBookingStartTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStartTime().format(timeFmt)));
        tableColumnBookingEndTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEndTime().format(timeFmt)));
        tableColumnBookingStatus.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus().name()));
        tableColumnBookingHairdresserName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getHairdresserName()));
        tableColumnBookingCustomerId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getCustomerId()));

        //Updater treatment tableview når man vælger et element i booking tableview
        tableViewBooking.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected == null){
                specificBookingTreatmentsObservableList.clear();
            } else {
                refreshTreatmentTable(selected.getBookingId());
            }
        });


        listViewHairTreatment.setItems(chosenHairTreatmentObservableList);

        tableViewSpecificTreatments.setItems(specificBookingTreatmentsObservableList);

        tableColumnTreatmentTreatmentId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()));
        tableColumnTreatmentName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        tableColumnTreatmentDuration.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getDuration()));


    }


    //Tilføj HairTreatment til listview
    @FXML
    private void onClickAddTreatmentToList() {
        HairTreatment hT = comboBoxHairTreatment.getSelectionModel().getSelectedItem();
        try {
            service.validateAddTreatment(chosenHairTreatmentObservableList.toArray(new HairTreatment[0]), hT);
            chosenHairTreatmentObservableList.add(hT);
        } catch (ValidationException ve) {
            exception.showAlert("Valideringsfejl", ve.getMessage());
        }
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

        refreshBookingTable();
    }

    //Sæt Booking til PENDING
    @FXML
    private void onClickSetBookingToPending() {
        Booking b = tableViewBooking.getSelectionModel().getSelectedItem();

        b.setStatus(Status.PENDING);

        try {
            service.handleUpdateBooking(b); //Opdaterer Booking i databasen
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        refreshBookingTable();
    }

    //Sæt Booking til COMPLETED
    @FXML
    private void onClickSetBookingToCompleted() {
        Booking b = tableViewBooking.getSelectionModel().getSelectedItem();

        b.setStatus(Status.COMPLETED);

        try {
            service.handleUpdateBooking(b); //Opdaterer Booking i databasen
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        refreshBookingTable();
    }

    //Vis CANCELLED Bookings ved check
    @FXML
    private void onCheckShowCancelledBookings() {
        refreshBookingTable();
    }

    //Viser Bookings ud fra dato
    @FXML
    private void datePickerSelectBookingDate() {
        refreshBookingTable();
    }

    //Tilføjer Booking
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
            int hairdresserId = hairdresser.getHairdresserId();

            String customerName = textFieldBookingCustomerName.getText().trim();
            if (customerName.isBlank()) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden et navn");
            }

            String phoneNumber = textFieldBookingPhoneNumber.getText().trim();
            if (phoneNumber.isBlank()) {
                throw new IllegalArgumentException("Der kan ikke tilføjes en booking uden et telefonnummer");
            }


            LocalTime suggestedEnd = service.validateBooking(date, suggestedTime, hairdresserId, chosenTreatments);

            //Prøver at finde kunden i de allerede kendte kunder
            int customerId = service.handlePersonExists(customerName, phoneNumber);

            //Hvis de ikke umiddelbart kunne findes i registeret
            if (customerId == -1) {
                int generatedPersonId = service.handleAddPerson(customerName, phoneNumber);
                customerId = service.handleAddCustomer(generatedPersonId);
            }

            service.handleAddBooking(date, suggestedTime, suggestedEnd, hairdresserId, customerId, chosenTreatments);

            refreshBookingTable();

        } catch (IllegalArgumentException iae) {
            exception.showAlert("Vær Opmærksom På", iae.getMessage());
        } catch (ValidationException ve) {
            exception.showAlert("Valideringsfejl", ve.getMessage());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        } catch (Exception e) {
            exception.showAlert("Ukendt Fejl", "En ukendt fejl er sket i forbindelse med tilføjelse af booking");
        }
    }

    //Når bruger logger ud skifter scene til login-view
    @FXML
    private void switchToLogin() {
        try {
            navigator.goTo("login-view.fxml", "Login");
        } catch (RuntimeException re) {
            exception.showAlert("Display Fejl", re.getMessage());
        }
    }

    //Genindlæser Booking tableView
    private void refreshBookingTable() {
        try {
            List<Booking> all = service.handleGetBookingsByDate(datePickerBooking.getValue(), checkBoxShowCancelledBookings.isSelected());
            visibleBookingsObservableList.setAll(all);
        } catch (DataAccessException dae) {
            exception.showAlert("Database Fejl", dae.getMessage());
        }
    }

    //Indlæser Treatment tableview med de givne behandlinger for den valgte booking
    private void refreshTreatmentTable(int bookingId){
        try {
            List<HairTreatment> specific = service.handleGetSpecificTreatments(bookingId);
            specificBookingTreatmentsObservableList.setAll(specific);
        } catch (DataAccessException dae){
            exception.showAlert("Database Fejl", dae.getMessage());
        }
    }
}
