package ru.alexksysx.objects;

public class Model {
    private Long codModel;
    private String nameModel;
    private Integer places;
    private Long modelClass;

    public Model(String nameModel, Integer places, Long modelClass) {
        this.nameModel = nameModel;
        this.places = places;
        this.modelClass = modelClass;
    }

    public Model() {
    }

    public Long getCodModel() {
        return codModel;
    }

    public void setCodModel(Long codModel) {
        this.codModel = codModel;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Long getModelClass() {
        return modelClass;
    }

    public void setModelClass(Long modelClass) {
        this.modelClass = modelClass;
    }
}
