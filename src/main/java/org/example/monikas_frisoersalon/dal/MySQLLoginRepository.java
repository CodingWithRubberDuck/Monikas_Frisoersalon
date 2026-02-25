package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySQLLoginRepository implements LoginRepository{
    private final DBConnection db;

    public MySQLLoginRepository(DBConnection db){
        this.db = db;
    }

    @Override
    public Optional<Person> getPasswordHash(String email) {
        String sql = "SELECT * FROM person WHERE email = ?";

        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(new Person(
                            rs.getInt("person_id"),
                            rs.getString("name"),
                            rs.getString("phone_number"),
                            rs.getString("email"),
                            rs.getString("password_hash")));
                }
                return Optional.empty();
            }

        } catch (SQLException sqle) {
            throw new DataAccessException("Det gik noget galt i forbindelsen med databasen", sqle);
        }
    }






}
