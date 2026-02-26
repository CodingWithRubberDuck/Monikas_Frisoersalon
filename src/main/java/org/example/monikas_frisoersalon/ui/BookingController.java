package org.example.monikas_frisoersalon.ui;

import javafx.fxml.FXML;

import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.logic.BookingService;



public class BookingController {

    private final BookingService service;
    private final Navigator navigator;
    private final ExceptionController exception;

    public BookingController(BookingService service, Navigator navigator, ExceptionController exception){
        this.service = service;
        this.navigator = navigator;
        this.exception = exception;
    }



    @FXML
    private void switchToLogin() {
        try {
            navigator.goTo("login-view.fxml", "Login");
        } catch (RuntimeException re){
            exception.showAlert("Display Fejl", re.getMessage());
        }

    }









}
