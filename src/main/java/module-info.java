module org.example.monikas_frisoersalon {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.monikas_frisoersalon to javafx.fxml;
    exports org.example.monikas_frisoersalon;
    exports dal;
    opens dal to javafx.fxml;
    exports logic;
    opens logic to javafx.fxml;
    exports ui;
    opens ui to javafx.fxml;
}