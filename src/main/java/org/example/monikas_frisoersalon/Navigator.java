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
            //Opretter loaderen og får fat på fxml-filen som skal bruges
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlFileName));
            //Loaderen bruger produktet af context's create metode til at lave controllerFactory
            // (forsimplet sagt måden controlleren laves på)
            loader.setControllerFactory(context::create);

            //Definerer root (essentielt set indholdet af scenen) baseret på hvad loader nu kan loade
            Parent root = loader.load();
            //Scenen får sat root som indhold
            scene.setRoot(root);
            //Vinduet/skærmen får givet titel
            stage.setTitle(title);
            //Vinduet bliver tilpasset til scenen
            stage.sizeToScene();
        } catch (IOException ioe){
            throw new RuntimeException("Kunne ikke loade FXML: " + fxmlFileName, ioe);
        }
    }

}
