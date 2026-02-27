package org.example.monikas_frisoersalon;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.monikas_frisoersalon.dal.MySQLBookingRepository;
import org.example.monikas_frisoersalon.dal.MySQLLoginRepository;
import org.example.monikas_frisoersalon.dal.MySQLPersonRepository;
import org.example.monikas_frisoersalon.dal.MySQLTreatmentRepository;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.logic.BookingService;
import org.example.monikas_frisoersalon.logic.LoginService;
import org.example.monikas_frisoersalon.ui.ExceptionController;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        //Hvor der oprettes og gemmes instanser af objekter
        AppContext context = new AppContext();

        //Klassen som har kontakt med databasen
        DBConnection db = new DBConnection();

        //Opretter de nødvendige instanser af objekter som skal registreres i systemet ved at gemmes i context
        context.registerInstance(LoginService.class, new LoginService(new MySQLLoginRepository(db)));
        context.registerInstance(BookingService.class, new BookingService(new MySQLBookingRepository(db), new MySQLPersonRepository(db), new MySQLTreatmentRepository(db)));
        context.registerInstance(ExceptionController.class, new ExceptionController());

        //Opretter objekt af navigatoren og registreres/gemmes i context
        Navigator navigator = new Navigator(stage, context);
        context.registerInstance(Navigator.class, navigator);

        //Kalder en metode i Navigator klassen som starter programmet
        navigator.start("login-view.fxml", "Login");
    }
}
