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

    //Eksisterer så customer kan have en constructor med kun customer_id
    public Person(){
    }

    /// Getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    /// Setters
    public void setName (String name) {
        this.name = name;
    }

    /// toString
    @Override
    public String toString() {
        return getName() + " " + getPhoneNumber();
    }
}
