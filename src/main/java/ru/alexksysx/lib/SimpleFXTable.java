package ru.alexksysx.lib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SimpleFXTable<T> {
    private ObservableList<T> list;
    private TableView<T> tableView;
    private List<TableColumn> columns;

    private SimpleFXTable(Builder builder) {
        this.columns = builder.columns;
        this.list = builder.list == null ? FXCollections.emptyObservableList() : FXCollections.observableList(builder.list);
        this.tableView = builder.tableView;
    }

    public static class Builder<T> {
        private TableView<T> tableView;
        private List<TableColumn> columns;
        private List<T> list;

        public Builder(TableView<T> tableView) {
            this.tableView = tableView;
        }

        public <C> Builder withColumn(TableColumn column, String columnName) {
            column.setCellFactory(new PropertyValueFactory<T, C>(columnName));
            columns.add(column);
            return this;
        }

        public Builder withData(List<T> list) {
            list = FXCollections.observableList(list);
            return this;
        }

        public SimpleFXTable build() {
            return new SimpleFXTable(this);
        }

    }
}
