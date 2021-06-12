package ru.alexksysx.dispatcher;

public class PointsTripsView {
    private Long codPoint;
    private Long codTrip;
    private String nameRoute;
    private Integer weekDay;
    private String time;
    private Long modelClass;
    private Integer freePlaces;

    public void setCodPoint(Long codPoint) {
        this.codPoint = codPoint;
    }

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

    public void setTime(String time) {
        this.time = time;
    }

    public void setModelClass(Long modelClass) {
        this.modelClass = modelClass;
    }

    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }
}
