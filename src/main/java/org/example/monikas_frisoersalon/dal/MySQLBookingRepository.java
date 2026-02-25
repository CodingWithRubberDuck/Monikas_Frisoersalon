package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.infrastructure.DBConnection;

public class MySQLBookingRepository implements BookingRepository{
    private final DBConnection db;

    public MySQLBookingRepository(DBConnection db){
        this.db = db;
    }
}
