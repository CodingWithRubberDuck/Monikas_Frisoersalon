package org.example.monikas_frisoersalon;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.dal.MySQLBookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLLoginRepository;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.logic.LoginService;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        AppContext context = new AppContext();

        DBConnection db = new DBConnection();
        MySQLLoginRepository loginRepository = new MySQLLoginRepository(db);
        MySQLBookingRepository bookingRepository = new MySQLBookingRepository(db);

        context.registerInstance(LoginService.class, new LoginService(loginRepository));
        context.registerInstance(BookingService.class, new BookingService(bookingRepository));
        Navigator navigator = new Navigator(stage, context);
        context.registerInstance(Navigator.class, navigator);

        navigator.start("login-view.fxml", "Login");
    }
}
