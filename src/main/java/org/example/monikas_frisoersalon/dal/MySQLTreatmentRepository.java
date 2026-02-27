package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.infrastructure.DBConnection;

public class MySQLTreatmentRepository implements TreatmentRepository{

    private final DBConnection db;

    public MySQLTreatmentRepository(DBConnection db){
        this.db = db;
    }

}
