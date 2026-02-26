package org.example.monikas_frisoersalon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.logic.BookingService;



public class BookingController {

    private final BookingService service;
    private final Navigator navigator;

    public BookingController(BookingService service, Navigator navigator){
        this.service = service;
        this.navigator = navigator;
    }



    @FXML
    private void switchToLogin() {
        navigator.goTo("login-view.fxml", "Login");
    }









}
