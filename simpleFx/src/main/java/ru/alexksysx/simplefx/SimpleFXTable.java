package ru.alexksysx.simplefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleFXTable<T> {
    private ObservableList<T> list;
    private TableView<T> tableView;
    private List<TableColumn> columns;

    private SimpleFXTable(Builder builder) {
        this.columns = builder.columns;
        list = FXCollections.observableArrayList();
        list.addAll(builder.list == null ? Collections.emptyList() : builder.list);
        this.tableView = builder.tableView;
        tableView.setItems(list);
    }

    /**
     * Разрешить редактирование таблицы
     * @param editable
     */
    public void setEditable(boolean editable) {
        tableView.setEditable(editable);
    }

    /**
     * Получить выделенный ряд в таблице
     * @return выделенный элемент
     */
    public T getSelectedRow() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Получить индекс выбранного ряда
     * @return индекс выбранного ряда. -1 в случае, если ряд не выбран.
     */
    public Integer getSelectedRowIndex() {
        return tableView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Получить ряд по индексу
     * @param index индекс
     * @return ряд
     */
    public T getRowByIndex(int index) {
        return tableView.getItems().get(index);
    }

    /**
     * Удалить ряд по индексу
     * @param index индекс ряда
     * @return Предыдущее значение
     */
    public T removeRowByIndex(int index) {
        return tableView.getItems().remove(index);
    }

    /**
     * Добавить новую строку в таблицу
     * @param item элемент
     */
    public void addRow(T item) {
        list.add(item);
    }

    /**
     * Очистить всю таблицу
     */
    public void cleanTable() {
        tableView.getItems().clear();
    }

    /**
     * Получить все элементы из таблицы
     * @return список элементов
     */
    public List<T> getAllElements() {
        return tableView.getItems();
    }

    public ObservableList<T> getObservableList() {
        return list;
    }

    /**
     * Добавить в таблицу список объектов
     * @param list список объектов
     */
    public void setElementList(List<T> list) {
        tableView.getItems().addAll(list);
    }

    public void removeElement(T element) {
        tableView.getItems().remove(element);
    }

    public static class Builder<T> {
        private TableView<T> tableView;
        private List<TableColumn> columns;
        private List<T> list;

        public Builder(TableView<T> tableView) {
            this.tableView = tableView;
            columns = new ArrayList<>();
        }

        public Builder withIntegerColumn(TableColumn column, String classFieldName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Integer>(classFieldName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            columns.add(column);
            return this;
        }

        public Builder withStringColumn(TableColumn column, String classFieldName) {
            column.setCellValueFactory(new PropertyValueFactory<T, String>(classFieldName));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            columns.add(column);
            return this;
        }

        public Builder withDoubleColumn(TableColumn column, String classFieldName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Double>(classFieldName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            columns.add(column);
            return this;
        }

        public Builder withFloatColumn(TableColumn column, String classFieldName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Float>(classFieldName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            columns.add(column);
            return this;
        }

        public Builder withLongColumn(TableColumn column, String classFieldName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Long>(classFieldName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
            return this;
        }

        public Builder isEditable(boolean isEditable) {
            tableView.setEditable(isEditable);
            return this;
        }

        public Builder withData(List<T> list) {
            this.list = list;
            return this;
        }

        public SimpleFXTable build() {
            return new SimpleFXTable(this);
        }

    }
}
