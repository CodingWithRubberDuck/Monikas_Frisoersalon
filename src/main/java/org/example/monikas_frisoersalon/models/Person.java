package org.example.monikas_frisoersalon.models;

public class Person {
    private int personId;
    private String name;
    private String phoneNumber;
    private String email;
    private String passwordHash;

    public Person(int personId, String name, String phoneNumber, String email, String passwordHash) {
        this.personId = personId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Person(int personId, String name){
        this.personId = personId;
        this.name = name;
    }

    /// Getters
    public int getPersonId(){
        return personId;
    }


    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash(){
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
