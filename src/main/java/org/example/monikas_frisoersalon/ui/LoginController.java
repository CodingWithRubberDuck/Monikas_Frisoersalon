package org.example.monikas_frisoersalon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.exceptions.DatabaseConnectionException;
import org.example.monikas_frisoersalon.logic.BookingService;

import java.io.IOException;


public class LoginController {

    private final BookingService service;
    private final Navigator navigator;


    public LoginController(BookingService service, Navigator navigator){
        this.service = service;
        this.navigator = navigator;
    }


    @FXML
    TextField textFieldInputEmail;

    @FXML
    PasswordField textFieldInputPassword;








    @FXML
    private void onButtonClickTryToLogin(ActionEvent event) throws IOException {
        boolean correctpassword = false;
        try {
            correctpassword = service.manageLogin(textFieldInputEmail.getText(), textFieldInputPassword.getText());
        } catch (DataAccessException dae){
            System.out.println(dae.getMessage());
        } catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
        } catch (DatabaseConnectionException dce){
            System.out.println(dce.getMessage());
        }

        textFieldInputEmail.clear();
        textFieldInputPassword.clear();
        if (correctpassword) {
            switchToBooking();
        }
    }


    private void switchToBooking(){
        navigator.goTo("booking-view.fxml", "Monikas Salon");
    }



}
