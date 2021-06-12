package ru.alexksysx.dispatcher;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.alexksysx.objects.Point;
import ru.alexksysx.simplefx.SimpleFXTable;

import javax.sql.DataSource;

public class CashierController {
    private CashierDao cashierDao;
    @FXML
    private ChoiceBox<Point> pointChoice;
    @FXML
    private Label distanceLabel;
    @FXML
    private TableView<PointsTripsView> cashierTable;
    @FXML
    private TableColumn<PointsTripsView, String> routeNameColumn;
    @FXML
    private TableColumn<PointsTripsView, Integer> dayColumn;
    @FXML
    private TableColumn<PointsTripsView, String> timeColumn;
    @FXML
    private TableColumn<PointsTripsView, Long> classColumn;
    @FXML
    private TableColumn<PointsTripsView, Integer> placesColumn;
    @FXML
    private TextField ticketsInput;
    private SimpleFXTable<PointsTripsView> simplePointsTripsTable;



    @FXML
    public void initialize() {
        DataSource ds = new DriverManagerDataSource(
                "jdbc:oracle:thin:@192.168.0.15:1527:orcl",
                "test_user", "test_user");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        cashierDao = new CashierDao(jdbcTemplate);
        pointChoice.setItems(FXCollections.observableList(cashierDao.getAllPoints()));
        pointChoice.getSelectionModel().select(0);
        simplePointsTripsTable = new SimpleFXTable.Builder<>(cashierTable)
                .withStringColumn(routeNameColumn, "nameRoute")
                .withIntegerColumn(dayColumn, "weekDay")
                .withStringColumn(timeColumn, "time")
                .withLongColumn(classColumn, "modelClass")
                .withIntegerColumn(placesColumn, "freePlaces")
                .build();
        Point firstPoint = pointChoice.getSelectionModel().getSelectedItem();
        if (firstPoint != null) {
            updateData();
        }
        pointChoice.setOnAction(actionEvent -> updateData());
    }

    private void updateData() {
        Point point = pointChoice.getSelectionModel().getSelectedItem();
        distanceLabel.setText(point.getDistance().toString());
        simplePointsTripsTable.cleanTable();
        simplePointsTripsTable.setElementList(cashierDao.getTripsByCodPoint(point.getCodPoint()));
    }

    public void sellTickets(ActionEvent actionEvent) {
        Point point = pointChoice.getSelectionModel().getSelectedItem();
        PointsTripsView trip = simplePointsTripsTable.getSelectedRow();
        Integer tickets = Integer.parseInt(ticketsInput.getText());
        if (trip != null) {
            String price;
            try {
                price = cashierDao.saleTickets(trip.getCodTrip(), point.getCodPoint(), tickets);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Продажа билотов");
                alert.setContentText("Сумма: " + price);
                alert.showAndWait();
                ticketsInput.clear();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Не удалось продать билеты");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } finally {
                updateData();
            }
        }
    }
}
