package org.example.monikas_frisoersalon.models;

public class Person {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String passwordHash;

    public Person(int id, String name, String phoneNumber, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    /// Getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return passwordHash;
    }

    /// Setters
    public void setName (String name) {
        this.name = name;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    /// toString
    @Override
    public String toString() {
        return getName() + " " + getPhoneNumber();
    }
}
