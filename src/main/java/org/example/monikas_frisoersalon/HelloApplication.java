package org.example.monikas_frisoersalon;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.dal.MySQLBookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLLoginRepository;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.logic.BookingService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DBConnection db = new DBConnection();
        MySQLLoginRepository loginRepository = new MySQLLoginRepository(db);
        MySQLBookingRepository bookingRepository = new MySQLBookingRepository(db);

        BookingService service = new BookingService(loginRepository, bookingRepository);
        Loader loader = new Loader(service);

        Parent root = loader.load("/org/example/monikas_frisoersalon/login-view.fxml");
        Scene scene = new Scene(root, 960, 640);
        stage.setTitle("Monikas Frisørsalon");
        stage.setScene(scene);
        stage.show();
    }
}
