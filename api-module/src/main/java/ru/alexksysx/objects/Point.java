package ru.alexksysx.objects;

public class Point {
    private Long codPoint;
    private String namePoint;
    private Long distance;

    public Point(Long codPoint, String namePoint, Long distance) {
        this.codPoint = codPoint;
        this.namePoint = namePoint;
        this.distance = distance;
    }

    public Point() {
    }

    public Long getCodPoint() {
        return codPoint;
    }

    public void setCodPoint(Long codPoint) {
        this.codPoint = codPoint;
    }

    public String getNamePoint() {
        return namePoint;
    }

    public void setNamePoint(String namePoint) {
        this.namePoint = namePoint;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
