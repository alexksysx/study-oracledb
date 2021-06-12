package ru.alexksysx.dispatcher;

public class TripsNowView {
    private Long codTrip;
    private String nameRoute;
    private Integer weekDay;
    private Integer hour;
    private Integer minute;
    private Integer tickets;

    public Long getCodTrip() {
        return codTrip;
    }

    public void setCodTrip(Long codTrip) {
        this.codTrip = codTrip;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
}
