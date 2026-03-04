package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.Customer;
import org.example.monikas_frisoersalon.models.Hairdresser;
import org.example.monikas_frisoersalon.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLPersonRepository implements PersonRepository {
    private final DBConnection db;

    public MySQLPersonRepository(DBConnection db) {
        this.db = db;
    }

    @Override
    public List<Hairdresser> showAllHairdressers() {
        List<Hairdresser> hairdressers = new ArrayList<>();

        String sql = "SELECT person.person_id, person.name, hairdresser.hairdresser_id " +
                "FROM hairdresser " +
                "JOIN person ON hairdresser.person_id = person.person_id";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            //Løkke kører igennem alle Hairdressers og stopper når der ikke er flere
            while (rs.next()) {
                int personId = rs.getInt("person_id");
                String name = rs.getString("name");
                int hairdresserId = rs.getInt("hairdresser_id");

                Hairdresser h = new Hairdresser(personId, name, hairdresserId); //Bruger constructor i Hairdresser til at oprette objekt
                hairdressers.add(h);
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Det gik noget galt i forbindelsen med databasen", sqle);

        }

        return hairdressers;
    }

    @Override
    public Optional<Customer> existsInCustomer(String name, String phoneNumber) {
        String sql = "SELECT customer.customer_id " +
                "FROM customer " +
                "Join person on customer.person_id = person.person_id " +
                "WHERE name = ? AND phone_number = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phoneNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(rs.getInt("customer_id")));
                }
                return Optional.empty();
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Fejl ved Tjek af Person i databasen", sqle);
        }
    }

    public int addPerson(String name, String phoneNumber) {
        String sql = "INSERT INTO person (name, phone_number) values (?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, phoneNumber);

            ps.executeUpdate();

            int generatedId = 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }
            return generatedId;
        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med tilføjelsen af en ny person", sqle);
        }
    }

    @Override
    public int addCustomer(int personId) {
        String sql = "INSERT INTO customer (person_id) value (?);";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, personId);

            ps.executeUpdate();

            int generatedId = 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }
            return generatedId;
        } catch (SQLException sqle) {
            throw new DataAccessException("Der gik noget galt i forbindelse med tilføjelsen af en ny kunde", sqle);
        }
    }
}
