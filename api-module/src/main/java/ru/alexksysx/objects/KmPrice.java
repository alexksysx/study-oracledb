package ru.alexksysx.objects;

public class KmPrice {
    private Long modelClass;
    private Double price;

    public KmPrice(Long modelClass, Double price) {
        this.modelClass = modelClass;
        this.price = price;
    }

    public KmPrice() {
    }

    public Long getModelClass() {
        return modelClass;
    }

    public void setModelClass(Long modelClass) {
        this.modelClass = modelClass;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return modelClass.toString();
    }
}

