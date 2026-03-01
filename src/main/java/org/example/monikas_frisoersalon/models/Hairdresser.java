package org.example.monikas_frisoersalon.models;

public class Hairdresser extends Person{

    private int hairdresserId;

    public Hairdresser(int personId, String name, String phoneNumber, String email, String password, int hairdresserId) {
        super(personId, name, phoneNumber, email, password);
        this.hairdresserId = hairdresserId;
    }

    public Hairdresser(int personId, String name, int hairdresserId) {
        super(personId, name);
        this.hairdresserId = hairdresserId;
    }


    public int getHairdresserId() {
        return hairdresserId;
    }

    @Override
    public String toString(){
        return getName();
    }
}
