package org.example.monikas_frisoersalon.dal;

import javafx.fxml.FXML;
import org.example.monikas_frisoersalon.exceptions.DataAccessException;
import org.example.monikas_frisoersalon.infrastructure.DBConnection;
import org.example.monikas_frisoersalon.models.Booking;
import org.example.monikas_frisoersalon.models.HairTreatment;
import org.example.monikas_frisoersalon.models.Status;

import java.sql.*;
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
    public List<Booking> findAllByDate(LocalDate date, boolean showCancelled) {
        List<Booking> result = new ArrayList<>();

        String sql;

        if (showCancelled) {
            sql = "SELECT booking.booking_id, booking.date, booking.start_time, booking.end_time, booking.hairdresser_id, booking.customer_id, booking.status, person.name " +
                    "FROM booking " +
                    "JOIN hairdresser ON booking.hairdresser_id = hairdresser.hairdresser_id " +
                    "Inner Join person on hairdresser.person_id = person.person_id " +
                    "WHERE booking.date = ?";
        } else {
            sql = "SELECT booking.booking_id, booking.date, booking.start_time, booking.end_time, booking.hairdresser_id, booking.customer_id, booking.status, person.name " +
                    "FROM booking " +
                    "JOIN hairdresser ON booking.hairdresser_id = hairdresser.hairdresser_id " +
                    "Inner Join person on hairdresser.person_id = person.person_id " +
                    "WHERE booking.date = ? AND booking.status != 'CANCELLED'";
        }

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date.toString());
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    result.add(mapRowPlusName(rs));
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
    public List<Booking> findSpecificBookings(LocalDate date, int hairdresserId) {
        List<Booking> result = new ArrayList<>();

        String sql = "SELECT booking_id, date, start_time, end_time, hairdresser_id, customer_id, status " +
                "FROM booking WHERE date = ? " +
                "AND hairdresser_id = ?  AND status = 'PENDING'";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date.toString());
            ps.setInt(2, hairdresserId);
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

    @Override
    public int addBooking(Booking newBooking) {
        String sql = "INSERT INTO booking (date, start_time, end_time, hairdresser_id, customer_id, status) values (?, ?, ?, ?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, newBooking.getDate().toString());
            ps.setString(2, newBooking.getStartTime().toString());
            ps.setString(3, newBooking.getEndTime().toString());
            ps.setInt(4, newBooking.getHairdresserId());
            ps.setInt(5, newBooking.getCustomerId());
            ps.setString(6, newBooking.getStatus().toString());

            ps.executeUpdate();

            int generatedId = 0;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }
            return generatedId;
        } catch (SQLException sqle) {
            throw new DataAccessException("Fejl ved tilføjelse af booking", sqle);
        }
    }

    public void addBookingTreatments(int treatmentId, int bookingId) {
        String sql = "INSERT INTO treatment_bookings (treatment_id, booking_id) values (?,?)";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, treatmentId);
            ps.setInt(2, bookingId);

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException("Fejl ved tilføjelse af booking", sqle);
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

    private Booking mapRowPlusName(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("booking_id"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("start_time")),
                LocalTime.parse(rs.getString("end_time")),
                rs.getInt("hairdresser_id"),
                rs.getInt("customer_id"),
                Status.valueOf(rs.getString("status")),
                rs.getString("name"));
    }
}
