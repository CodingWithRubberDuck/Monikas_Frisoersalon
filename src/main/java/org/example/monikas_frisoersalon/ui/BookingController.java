package org.example.monikas_frisoersalon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.models.Hairdresser;


public class BookingController {

    private final BookingService service;
    private final Navigator navigator;
    private final ExceptionController exception;

    @FXML
    ComboBox<Hairdresser> comboBoxHairdresser;

    public BookingController(BookingService service, Navigator navigator, ExceptionController exception) {
        this.service = service;
        this.navigator = navigator;
        this.exception = exception;
    }

    /// Opretter observable lists
    ObservableList<Hairdresser> hairdresserObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //Forbinder ObservableList med comboBox
        comboBoxHairdresser.setItems(hairdresserObservableList);

        //Henter data fra databasen og indsætter det
        hairdresserObservableList.addAll(service.handleShowAllHairdressers());
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
