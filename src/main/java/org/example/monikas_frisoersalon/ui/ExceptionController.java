package org.example.monikas_frisoersalon.ui;

import javafx.scene.control.Alert;

public class ExceptionController {
    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
