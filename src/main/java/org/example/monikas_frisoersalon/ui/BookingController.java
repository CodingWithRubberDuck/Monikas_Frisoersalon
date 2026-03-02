package org.example.monikas_frisoersalon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.models.Booking;
import org.example.monikas_frisoersalon.models.HairTreatment;
import org.example.monikas_frisoersalon.models.Hairdresser;
import org.example.monikas_frisoersalon.models.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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


    /// Opretter observable lists
    private final ObservableList<Hairdresser> hairdresserObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> hairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<HairTreatment> chosenHairTreatmentObservableList = FXCollections.observableArrayList();
    private final ObservableList<Booking> visibleBookings = FXCollections.observableArrayList();


    //Formatter
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");


    @FXML
    public void initialize() {
        //Forbinder ObservableList med comboBox
        comboBoxHairdresser.setItems(hairdresserObservableList);
        comboBoxHairTreatment.setItems(hairTreatmentObservableList);

        //Henter data fra databasen og indsætter det
        try {
            hairdresserObservableList.setAll(service.handleShowAllHairdressers());
            hairTreatmentObservableList.setAll(service.handlegetAllHairTreatments());
        } catch (DataAccessException dae) {
            exception.showAlert("Databasefejl", dae.getMessage());
        }

        //Standard Dato
        datePickerBooking.setValue(LocalDate.now());

        tableViewBooking.setItems(visibleBookings);

        tableColumnBookingBookingId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getBookingId()));
        tableColumnBookingDate.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate().toString()));
        tableColumnBookingStartTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStartTime().format(timeFmt)));
        tableColumnBookingEndTime.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEndTime().format(timeFmt)));
        tableColumnBookingStatus.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus().name()));
        tableColumnBookingHairdresserId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getHairdresserId()));
        tableColumnBookingCustomerId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getCustomerId()));

        try {
            List<Booking> all = service.handleGetBookingsByDate(datePickerBooking.getValue());
            visibleBookings.setAll(all);
        } catch (DataAccessException dae) {
            exception.showAlert("Database Fejl", dae.getMessage());
        }

        listViewHairTreatment.setItems(chosenHairTreatmentObservableList);


    }

    //Tilføj hairtreatment til listview
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

    @FXML
    private void datePickerSelectBookingDate(){
        List<Booking> all = service.handleGetBookingsByDate(datePickerBooking.getValue());
        visibleBookings.setAll(all);
    }


    @FXML
    private void switchToLogin() {
        try {
            navigator.goTo("login-view.fxml", "Login");
        } catch (RuntimeException re) {
            exception.showAlert("Display Fejl", re.getMessage());
        }
    }


}
