module org.example.monikas_frisoersalon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires jbcrypt;


    opens org.example.monikas_frisoersalon to javafx.fxml;
    exports org.example.monikas_frisoersalon;
    exports org.example.monikas_frisoersalon.dal;
    opens org.example.monikas_frisoersalon.dal to javafx.fxml;
    exports org.example.monikas_frisoersalon.logic;
    opens org.example.monikas_frisoersalon.logic to javafx.fxml;
    exports org.example.monikas_frisoersalon.ui;
    opens org.example.monikas_frisoersalon.ui to javafx.fxml;
    exports org.example.monikas_frisoersalon.infrastructure;
    opens org.example.monikas_frisoersalon.infrastructure to javafx.fxml;
    exports org.example.monikas_frisoersalon.models;

}