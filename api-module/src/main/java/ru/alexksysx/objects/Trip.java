package ru.alexksysx.objects;

public class Trip {
    private Long codTrip;
    private Integer weekDay;
    private Integer hour;
    private Integer minute;
    private Long codRoute;
    private Long codBus;
    private String busNumber;
    private Integer tickets;

    public Trip(Integer weekDay, Integer hour,
                Integer minute, Long codRoute, Long codBus, Integer tickets) {
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
        this.codRoute = codRoute;
        this.codBus = codBus;
        this.tickets = tickets;
    }

    public Trip() {
    }

    public Long getCodTrip() {
        return codTrip;
    }

    public void setCodTrip(Long codTrip) {
        this.codTrip = codTrip;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Long getCodRoute() {
        return codRoute;
    }

    public void setCodRoute(Long codRoute) {
        this.codRoute = codRoute;
    }

    public Long getCodBus() {
        return codBus;
    }

    public void setCodBus(Long codBus) {
        this.codBus = codBus;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
}
