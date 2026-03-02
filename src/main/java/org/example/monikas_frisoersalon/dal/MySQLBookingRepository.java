package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.Booking;
import org.example.monikas_frisoersalon.models.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLBookingRepository implements BookingRepository{
    private final DBConnection db;

    public MySQLBookingRepository(DBConnection db){
        this.db = db;
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT booking_id, date, start_time, end_time, hairdresser_id, customer_id, status " +
                "FROM booking ORDER BY date";

        List<Booking> result = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
               result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException sqle){
            throw new DataAccessException("Noget gik galt i forbindelse med nedhentning af bookinger", sqle);
        }



    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("booking_id"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("start_time")),
                LocalTime.parse(rs.getString("end_time")),
                rs.getInt("hairdresser_id"),
                rs.getInt("customer_id"),
                Status.valueOf(rs.getString("status")));
    }
}
