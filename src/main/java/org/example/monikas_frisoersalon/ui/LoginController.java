package org.example.monikas_frisoersalon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.Loader;
import org.example.monikas_frisoersalon.logic.BookingService;

import java.io.IOException;

public class LoginController {

    private BookingService service;


    public LoginController(BookingService service){
        this.service = service;
    }






    private Stage bookingStage;
    private Scene bookingScene;
    private Parent bookingRoot;


    //This is definitely not Single Responsibility, but it does work and it does achieve sharing the same reference to service between controllers
    //The placement and way of doing this will likely change later, but the result aka dependence injection will remain.
    @FXML
    private void onButtonClickTryToLogin(ActionEvent event) throws IOException {

        switchToBooking(event);
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
