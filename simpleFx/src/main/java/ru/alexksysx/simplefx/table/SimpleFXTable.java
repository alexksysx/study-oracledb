package ru.alexksysx.simplefx.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

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

    public static class Builder<T> {
        private TableView<T> tableView;
        private List<TableColumn> columns;
        private List<T> list;

        public Builder(TableView<T> tableView) {
            this.tableView = tableView;
            columns = new ArrayList<>();
        }

        public Builder withIntegerColumn(TableColumn column, String columnName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Integer>(columnName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            columns.add(column);
            return this;
        }

        public Builder withStringColumn(TableColumn column, String columnName) {
            column.setCellValueFactory(new PropertyValueFactory<T, String>(columnName));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            columns.add(column);
            return this;
        }

        public Builder withDoubleColumn(TableColumn column, String columnName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Double>(columnName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            columns.add(column);
            return this;
        }

        public Builder withFloatColumn(TableColumn column, String columnName) {
            column.setCellValueFactory(new PropertyValueFactory<T, Float>(columnName));
            column.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            columns.add(column);
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
