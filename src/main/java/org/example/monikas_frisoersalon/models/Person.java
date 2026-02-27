package org.example.monikas_frisoersalon.models;

public class Person {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    public Person(int id, String name, String phoneNumber, String email, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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
        return password;
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
