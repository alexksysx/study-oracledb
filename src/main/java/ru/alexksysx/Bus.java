package ru.alexksysx;

public class Bus {
    private int id;
    private String name;
    private String number;
    private String model;

    public Bus(int id, String name, String number, String model) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.model = model;
    }

    public Bus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
