package org.example.monikas_frisoersalon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Navigator (En scene manager)
 *
 * Vigtige pointer:
 * - Man kan bruge same 'Stage' hele tiden.
 * - Man kan bruge samme 'Scene' og skift kun root (scene.setRoot(root))
 * - Hver gang du loader en FXML: ny FXMLLoader + setControllerFactory(...) før load()
 */

public class Navigator {

    private final Stage stage;
    private final AppContext context;
    private final Scene scene;

    public Navigator(Stage stage, AppContext context){
        this.stage = stage;
        this.context = context;

        // Der laves én Scene, som vil genbruges, Root vil udskiftes ved navigation
        this.scene = new Scene(new javafx.scene.layout.BorderPane(), 960, 640);
        this.stage.setScene(scene);
    }


    public void start(String fxmlFileName, String title) {
        goTo(fxmlFileName, title);
        stage.show();
    }

    public void goTo(String fxmlFileName, String title){
        try {
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlFileName));

            loader.setControllerFactory(context::create);

            Parent root = loader.load();

            scene.setRoot(root);

            stage.setTitle(title);

            stage.sizeToScene();
        } catch (IOException ioe){
            throw new RuntimeException("Kunne ikke loade FXML: " + fxmlFileName, ioe);
        }
    }

}
