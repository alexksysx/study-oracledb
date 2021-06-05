package ru.alexksysx.simplefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;

import java.lang.reflect.Method;

public class SimpleFxPagination<T> {
    private Pagination pagination;
    private ObservableList list;
    private Button addPage;
    private Integer selectedIndex;
    private TextField pageNumber;

    public SimpleFxPagination(Pagination pagination, Button button) {
        this(pagination, button, FXCollections.observableArrayList());
    }

    public SimpleFxPagination(Pagination pagination, Button button, ObservableList list) {
        this.pagination = pagination;
        this.list = list;
        this.addPage = button;
    }

    public void add(Method method) {
        pagination.currentPageIndexProperty().addListener(((observableValue, oldIndex, newIndex) -> {

        }));
    }

}
