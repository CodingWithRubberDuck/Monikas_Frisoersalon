module org.example.monikas_frisoersalon {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.monikas_frisoersalon to javafx.fxml;
    exports org.example.monikas_frisoersalon;
}