package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.infrastructure.DBConnection;

public class MySQLPersonRepository implements PersonRepository{
    private final DBConnection db;

    public MySQLPersonRepository(DBConnection db){
        this.db = db;
    }
}
