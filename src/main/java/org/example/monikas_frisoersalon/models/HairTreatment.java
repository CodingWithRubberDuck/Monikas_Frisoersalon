package org.example.monikas_frisoersalon.models;

public class HairTreatment {
    private int id;
    private String name;
    private int duration;

    public HairTreatment(int id, String name, int duration){
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString(){
        return name + "  " + duration + " minutter";
    }
}
