package org.example.monikas_frisoersalon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.monikas_frisoersalon.Navigator;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.exceptions.DatabaseConnectionException;
import org.example.monikas_frisoersalon.logic.LoginService;


public class LoginController {

    private final LoginService service;
    private final Navigator navigator;
    private final ExceptionController exception;


    public LoginController(LoginService service, Navigator navigator, ExceptionController exception){
        this.service = service;
        this.navigator = navigator;
        this.exception = exception;
    }


    @FXML
    TextField textFieldInputEmail;

    @FXML
    PasswordField textFieldInputPassword;






    @FXML
    private void onButtonClickTryToLogin() {
        try {
            boolean correctpassword = service.manageLogin(textFieldInputEmail.getText(), textFieldInputPassword.getText());
            if (correctpassword) {
                switchToBooking();
            } else {
                exception.showAlert("Login Fejl", "Denne email eller dette kodeord er ugyldigt");
            }
        } catch (DataAccessException dae){
            exception.showAlert("Database Fejl", dae.getMessage());
        } catch (IllegalArgumentException iae){
            exception.showAlert("Login Fejl", iae.getMessage());
        } catch (DatabaseConnectionException dce){
            exception.showAlert("Fejl ved Databaseforbindelse", dce.getMessage());
        }
        textFieldInputEmail.clear();
        textFieldInputPassword.clear();
    }


    private void switchToBooking(){
        try {
            navigator.goTo("booking-view.fxml", "Monikas Salon");
        } catch (RuntimeException re){
            exception.showAlert("Display Fejl", re.getMessage());
        }

    }



}
