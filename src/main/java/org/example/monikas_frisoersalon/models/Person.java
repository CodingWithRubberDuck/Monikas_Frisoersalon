package org.example.monikas_frisoersalon.models;

public class Person {
    private String name;
    private int phoneNumber;
    private String email;
    private String password;

    public Person(String name, int phoneNumber, String email, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    /// Getters
    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    /// Setters
    public String setName (String name) {
        return name;
    }

    public int setPhoneNumber (int phoneNumber) {
        return phoneNumber;
    }

    public String setEmail (String email) {
        return email;
    }

    /// toString
    @Override
    public String toString() {
        return getName() + " " + getPhoneNumber();
    }
}
