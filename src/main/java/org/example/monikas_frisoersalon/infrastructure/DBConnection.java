package org.example.monikas_frisoersalon.infrastructure;

import org.example.monikas_frisoersalon.exceptions.DatabaseConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private final String URL;
    private final String USER;
    private final String PASS;

    public DBConnection(){
        Properties props = new Properties();

        try (InputStream input = getClass().getResourceAsStream("/org/example/monikas_frisoersalon/db.properties")){
            if (input == null){
                //throw new DatabaseConnectionException("Kunne ikke finde db.properties i resources");
            }
            props.load(input);
        } catch (IOException ioe) {
            //throw new DatabaseConnectionException("Kunne ikke læse db.properties", ioe);
        }
        this.URL = props.getProperty("db.url");
        this.USER = props.getProperty("db.user");
        this.PASS = props.getProperty("db.password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Kunne ikke oprette forbindelse til databasen", sqle);
        }
    }

}
