package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.infrastructure.DBConnection;

public class MySQLLoginRepository implements LoginRepository{
    private final DBConnection db;

    public MySQLLoginRepository(DBConnection db){
        this.db = db;
    }

}
