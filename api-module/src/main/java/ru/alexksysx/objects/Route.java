package ru.alexksysx.objects;

public class Route {
    private Long codRoute;
    private String nameRoute;

    public Route(Long codRoute, String nameRoute) {
        this.codRoute = codRoute;
        this.nameRoute = nameRoute;
    }

    public Route() {
    }

    public Long getCodRoute() {
        return codRoute;
    }

    public void setCodRoute(Long codRoute) {
        this.codRoute = codRoute;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }
}
