package ru.alexksysx.objects;

public class Bus {
    private Long codBus;
    private Long codModel;
    private String busNumber;

    public Bus(Long codModel, String busNumber) {
        this.codModel = codModel;
        this.busNumber = busNumber;
    }

    public Bus() {
    }

    public Long getCodBus() {
        return codBus;
    }

    public void setCodBus(Long codBus) {
        this.codBus = codBus;
    }

    public Long getCodModel() {
        return codModel;
    }

    public void setCodModel(Long codModel) {
        this.codModel = codModel;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    @Override
    public String toString() {
        return busNumber;
    }
}
