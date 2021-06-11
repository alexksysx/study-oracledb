package ru.alexksysx.cashier;

public class PointsTripsView {
    private Long codPoint;
    private Long codTrip;
    private String nameRoute;
    private Integer weekDay;
    private String time;
    private Long modelClass;
    private Integer freePlaces;

    public PointsTripsView(Long codPoint, Long codTrip, String nameRoute, Integer weekDay, String time, Long modelClass, Integer freePlaces) {
        this.codPoint = codPoint;
        this.codTrip = codTrip;
        this.nameRoute = nameRoute;
        this.weekDay = weekDay;
        this.time = time;
        this.modelClass = modelClass;
        this.freePlaces = freePlaces;
    }

    public PointsTripsView() {
    }

    public Long getCodPoint() {
        return codPoint;
    }

    public void setCodPoint(Long codPoint) {
        this.codPoint = codPoint;
    }

    public Long getCodTrip() {
        return codTrip;
    }

    public void setCodTrip(Long codTrip) {
        this.codTrip = codTrip;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getModelClass() {
        return modelClass;
    }

    public void setModelClass(Long modelClass) {
        this.modelClass = modelClass;
    }

    public Integer getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }
}
