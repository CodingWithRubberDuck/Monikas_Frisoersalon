package org.example.monikas_frisoersalon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.ui.BookingController;
import org.example.monikas_frisoersalon.ui.LoginController;

public class Loader {
    //private final SceneController sceneSwitch;
    private final BookingService service;

    public Loader(BookingService service){
        this.service = service;
    }

    public Parent load(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == LoginController.class){
                    return new LoginController(service);
                }
                if (controllerClass == BookingController.class){
                    return new BookingController(service);
                }

                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Kunne ikke lave controller: " + controllerClass.getName(), e);
                }

            });

            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Kunne ikke loade FXML: " + fxmlPath, e);
        }
    }

}
