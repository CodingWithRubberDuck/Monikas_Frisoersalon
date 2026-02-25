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

public class BookingController {

    private BookingService service;

    public BookingController(BookingService service){
        this.service = service;
    }


    private Stage loginStage;
    private Scene loginScene;
    private Parent loginRoot;



    //This is definitely not Single Responsibility, but it does work and it does achieve sharing the same reference to service between controllers
    //The placement and way of doing this will likely change later, but the result aka dependence injection will remain.
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        if (loginRoot == null) {
            Loader loader = new Loader(service);
            loginRoot = loader.load("/org/example/monikas_frisoersalon/login-view.fxml");
        }
        if (loginStage == null){
            loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        }
        if (loginScene == null){
            loginScene = new Scene(loginRoot);
        }
        loginStage.setScene(loginScene);
        loginStage.show();
    }









}
