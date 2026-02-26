package org.example.monikas_frisoersalon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.Loader;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.exceptions.DatabaseConnectionException;
import org.example.monikas_frisoersalon.logic.BookingService;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;

public class LoginController {

    private BookingService service;


    public LoginController(BookingService service){
        this.service = service;
    }


    @FXML
    TextField textFieldInputEmail;

    @FXML
    PasswordField textFieldInputPassword;








    private Stage bookingStage;
    private Scene bookingScene;
    private Parent bookingRoot;


    //This is definitely not Single Responsibility, but it does work and it does achieve sharing the same reference to service between controllers
    //The placement and way of doing this will likely change later, but the result aka dependence injection will remain.
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
            switchToBooking(event);
        }
    }


    private void switchToBooking(ActionEvent event) throws IOException{
        if (bookingRoot == null) {
            Loader loader = new Loader(service);
            bookingRoot = loader.load(("/org/example/monikas_frisoersalon/booking-view.fxml"));
        }
        if (bookingStage == null){
            bookingStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        }
        if (bookingScene == null){
            bookingScene = new Scene(bookingRoot);
        }
        bookingStage.setScene(bookingScene);
        bookingStage.show();
    }



}
