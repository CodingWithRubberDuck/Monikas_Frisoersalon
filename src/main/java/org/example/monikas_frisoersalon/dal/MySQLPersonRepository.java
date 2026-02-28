package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.Hairdresser;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonRepository implements PersonRepository {
    private final DBConnection db;

    public MySQLPersonRepository(DBConnection db) {
        this.db = db;
    }

    public List<Hairdresser> showAllHairdressers() {
        List<Hairdresser> hairdressers = new ArrayList<>();

        String sql = "SELECT hairdresser.hairdresser_id, person.name " +
                "FROM hairdresser" +
                "JOIN person ON hairdresser.person_id = person.person_id";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            //Løkke kører igennem alle Hairdressers og stopper når der ikke er flere
            while (rs.next()) {
                int id = rs.getInt("hairdresser_id");
                String name = rs.getString("name");

                Hairdresser h = new Hairdresser(id, name); //Bruger constructor i Hairdresser til at oprette objekt
                hairdressers.add(h);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Det gik noget galt i forbindelsen med databasen", sqle);

        }

        return hairdressers;
    }
}
