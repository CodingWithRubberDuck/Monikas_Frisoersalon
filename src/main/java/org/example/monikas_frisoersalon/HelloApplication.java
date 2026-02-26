package org.example.monikas_frisoersalon;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.dal.MySQLBookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLLoginRepository;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.logic.BookingService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AppContext context = new AppContext();

        DBConnection db = new DBConnection();
        MySQLLoginRepository loginRepository = new MySQLLoginRepository(db);
        MySQLBookingRepository bookingRepository = new MySQLBookingRepository(db);

        context.registerInstance(BookingService.class, new BookingService(loginRepository, bookingRepository));
        Navigator navigator = new Navigator(stage, context);
        context.registerInstance(Navigator.class, navigator);

        navigator.start("login-view.fxml", "Login");
    }
}
