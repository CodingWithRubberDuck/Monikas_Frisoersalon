package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.HairTreatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLTreatmentRepository implements TreatmentRepository {

    private final DBConnection db;

    public MySQLTreatmentRepository(DBConnection db) {
        this.db = db;
    }

    public List<HairTreatment> getAllHairTreatments() {
        List<HairTreatment> hairTreatments = new ArrayList<>();

        String sql = "SELECT * FROM treatment";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            //Løkke kører igennem alle HairTreatments og stopper når der ikke er flere
            while (rs.next()) {
                int id = rs.getInt("treatment_id");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");

                HairTreatment hT = new HairTreatment(id, name, duration); //Bruger constructor i HairTreatment til at oprette objekt
                hairTreatments.add(hT);
            }

        } catch (SQLException sqle) {
            throw new DataAccessException("Det gik noget galt i forbindelsen med databasen", sqle);
        }

        return hairTreatments;
    }

    @Override
    public List<HairTreatment> getSpecificTreatments(int bookingId) {
        List<HairTreatment> hairTreatments = new ArrayList<>();

        String sql = "SELECT treatment.treatment_id, treatment.name, treatment.duration " +
                "FROM treatment_bookings " +
                "INNER JOIN treatment on treatment_bookings.treatment_id = treatment.treatment_id " +
                "INNER JOIN booking on treatment_bookings.booking_id = booking.booking_id " +
                "WHERE treatment_bookings.booking_id = ?";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt("treatment_id");
                    String name = rs.getString("name");
                    int duration = rs.getInt("duration");

                    HairTreatment hT = new HairTreatment(id, name, duration); //Bruger constructor i HairTreatment til at oprette objekt
                    hairTreatments.add(hT);
                }
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Det gik noget galt i forbindelsen med databasen", sqle);
        }

        return hairTreatments;
    }
}
