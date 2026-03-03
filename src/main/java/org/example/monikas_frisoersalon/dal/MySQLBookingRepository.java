package org.example.monikas_frisoersalon.dal;

import javafx.fxml.FXML;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySQLBookingRepository implements BookingRepository {
    private final DBConnection db;


    public MySQLBookingRepository(DBConnection db) {
        this.db = db;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> result = new ArrayList<>();

        String sql = "SELECT booking_id, date, start_time, end_time, hairdresser_id, customer_id, status " +
                "FROM booking ORDER BY date";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException sqle) {
            throw new DataAccessException("Noget gik galt i forbindelse med nedhentning af bookinger", sqle);
        }
    }

    @FXML
    public List<Booking> findAllByDate(LocalDate date) {
        List<Booking> result = new ArrayList<>();

        String sql = "SELECT booking_id, date, start_time, end_time, hairdresser_id, customer_id, status " +
                "FROM booking WHERE date = ? ORDER BY start_time, hairdresser_id";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Noget gik galt i forbindelse med nedhentning af bookinger", sqle);
        }
        return result;
    }

    public Booking updateBooking(Booking booking) {
        String sql = "UPDATE booking SET status = ? WHERE booking_id = ?";

        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, booking.getStatus().toString());
            ps.setInt(2, booking.getBookingId());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Noget gik galt i forbindelse med opdatering af bookinger", sqle);
        }

        return booking;
    }

    @Override
    public List<Booking> findSpecificBookings(LocalDate date, int hairdresserId){
        List<Booking> result = new ArrayList<>();

        String sql = "SELECT booking_id, date, start_time, end_time, hairdresser_id, customer_id, status " +
                "FROM booking WHERE date = ? " +
                "AND hairdresser_id = ?  AND status = 'PENDING'";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, date.toString());
            ps.setInt(2, hairdresserId);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    result.add(mapRow(rs));
                }
            }
        } catch (SQLException sqle){
            throw new DataAccessException("Noget gik galt i forbindelse med nedhentning af bookinger", sqle);
        }
        return result;
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
