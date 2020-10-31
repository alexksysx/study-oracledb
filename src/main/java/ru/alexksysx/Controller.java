package ru.alexksysx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class Controller {
    private ObservableList<Bus> busesData = FXCollections.observableArrayList();
    private JdbcTemplate jdbcTemplate = Main.getJdbcTemplate();

    @FXML
    private TableView<Bus> tableBuses;

    @FXML
    private TableColumn<Bus, Integer> idColumn;

    @FXML
    private TableColumn<Bus, String> busNameColumn;

    @FXML
    private TableColumn<Bus, String> busNumberColumn;

    @FXML
    private TableColumn<Bus, String> busModelColumn;

    @FXML
    private TextField idInput;

    @FXML
    private TextField busNameInput;

    @FXML
    private TextField busNumberInput;

    @FXML
    private TextField busModelInput;

    public void insertRow() {
        Integer id;
        try {
            id = Integer.parseInt(idInput.getText());
        } catch (NumberFormatException e) {
            idInput.requestFocus();
            createAlert("Здесь нужно ввести число", "Ошибка");
            return;
        }
        String name = busNameInput.getText();
        String number = busNumberInput.getText();
        String model = busModelInput.getText();
        int update;
        try {
            update = jdbcTemplate.update("insert into test_user.buses values (?, ?, ?, ?)", id, name, number, model);
        } catch (DuplicateKeyException e) {
            createAlert("Запись с таким ключём уже существует", "Ошибка");
            return;
        }
        if (update > 0) {
            Bus bus = new Bus(id, name, number, model);
            busesData.add(bus);
        }
        idInput.clear();
        busNameInput.clear();
        busNumberInput.clear();
        busModelInput.clear();
    }

    private void createAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("id"));
        busNameColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("name"));
        busNumberColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("number"));
        busModelColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("model"));
        // заполняем таблицу данными
        tableBuses.setItems(busesData);
        // делаем колонны изменяемыми
        tableBuses.setEditable(true);
        busNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        busNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        busModelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    // подготавливаем данные для таблицы
    private void initData() {
        List<Bus> buses = jdbcTemplate.query("select * from test_user.buses", new BusRowMapper());
        busesData.addAll(buses);
    }

    public void busNameEditHandler(TableColumn.CellEditEvent<Bus, String> userStringCellEditEvent) {
        Bus bus = userStringCellEditEvent.getTableView().getItems().get(userStringCellEditEvent.getTablePosition().getRow());
        bus.setName(userStringCellEditEvent.getNewValue());
        if (!updateBus(bus)) {
            bus.setName(userStringCellEditEvent.getOldValue());
        }
    }

    public void modelEditHandler(TableColumn.CellEditEvent<Bus, String> userStringCellEditEvent) {
        Bus bus = userStringCellEditEvent.getTableView().getItems().get(userStringCellEditEvent.getTablePosition().getRow());
        bus.setModel(userStringCellEditEvent.getNewValue());
        if (!updateBus(bus)) {
            bus.setModel(userStringCellEditEvent.getOldValue());
        }
    }

    public void numberEditHandler(TableColumn.CellEditEvent<Bus, String> userStringCellEditEvent) {
        Bus bus = userStringCellEditEvent.getTableView().getItems().get(userStringCellEditEvent.getTablePosition().getRow());
        bus.setNumber(userStringCellEditEvent.getNewValue());
        if (!updateBus(bus)) {
            bus.setNumber(userStringCellEditEvent.getOldValue());
        }
    }

    private boolean updateBus(Bus bus) {
        int update = jdbcTemplate.update("update test_user.buses set bus_name = ?, bus_number = ?, bus_model = ? where id = ?",
                bus.getName(), bus.getNumber(), bus.getModel(), bus.getId());
        return update > 0;
    }

    private boolean deleteRow(Bus bus) {
        int delete = jdbcTemplate.update("delete from test_user.buses where id = ?", bus.getId());
        return delete > 0;
    }

    public void deleteRow(ActionEvent actionEvent) {
        int index = tableBuses.getSelectionModel().getFocusedIndex();
        if (index == -1)
            return;
        Bus bus = tableBuses.getItems().get(index);
        if (deleteRow(bus)) {
            tableBuses.getItems().remove(index);
        }
    }
}
