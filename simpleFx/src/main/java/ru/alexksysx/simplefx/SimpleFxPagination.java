package ru.alexksysx.simplefx;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;


public class SimpleFxPagination<T> {
    private Pagination pagination;
    private ObservableList list;
    private TextField pageNumber;

    public SimpleFxPagination(Pagination pagination) {
        this(pagination, Collections.emptyList());
    }

    public SimpleFxPagination(Pagination pagination, List list) {
        this.pagination = pagination;
        this.list = FXCollections.observableArrayList(list);
        pagination.setPageCount(list.size());
        updateTextField();

        pagination.currentPageIndexProperty().addListener(((observableValue, oldIndex, newIndex) -> {
            updateTextField();
        }));
    }

    public void setPageInput(TextField pageInput) {
        pageNumber = pageInput;
        updateTextField();
    }

    public void updateTextField() {
        if (list.size() == 0) {
            pagination.setDisable(true);
            return;
        }
        else
            pagination.setDisable(false);
        if (pageNumber != null) {
            pageNumber.setText(Integer.toString(pagination.getCurrentPageIndex()));
        }
    }

    public T getElement() {
        if (list.size() == 0)
            return null;
        return (T)list.get(pagination.getCurrentPageIndex());
    }

    public void addPage(T object) {
        list.add(object);
        pagination.setPageCount(list.size() - 1);
        updateTextField();
    }

    public void deletePage() {
        if (list.size() == 0)
            return;
        list.remove(pagination.getCurrentPageIndex());
        updateTextField();
    }

    public IntegerProperty currentPageIndexProperty() {
        return pagination.currentPageIndexProperty();
    }

}
