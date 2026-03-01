package org.example.monikas_frisoersalon.models;

public class Customer extends Person{

    private int customerId;

    public Customer(int id, String name, String phoneNumber, String email, String password, int customerId) {
        super(id, name, phoneNumber, email, password);
        this.customerId = customerId;
    }
}
