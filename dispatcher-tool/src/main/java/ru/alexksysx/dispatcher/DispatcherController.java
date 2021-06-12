package ru.alexksysx.dispatcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.alexksysx.simplefx.SimpleFXTable;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DispatcherController {
    @FXML
    private TableView<TripsNowView> dispatcherTable;
    @FXML
    private TableColumn<TripsNowView, Integer> ticketsColumn;
    @FXML
    private TableColumn<TripsNowView, Integer> hoursColumn;
    @FXML
    private TableColumn<TripsNowView, Integer> minutesColumn;
    @FXML
    private TableColumn<TripsNowView, String> routeNameColumn;
    @FXML
    private Label dateLabel;
    private SimpleFXTable<TripsNowView> simpleTripsTable;
    private DispatcherDao dispatcherDao;

    @FXML
    public void initialize() {
        DataSource ds = new DriverManagerDataSource(
                "jdbc:oracle:thin:@192.168.0.15:1527:orcl",
                "test_user", "test_user");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        dispatcherDao = new DispatcherDao(jdbcTemplate);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        String dayOfWeek = getDayOfWeek(date.getDayOfWeek());
        dateLabel.setText("Сегодня " + date.format(formatters) + ", " + dayOfWeek);
        simpleTripsTable = new SimpleFXTable.Builder<>(dispatcherTable)
                .withIntegerColumn(ticketsColumn, "tickets")
                .withIntegerColumn(hoursColumn, "hour")
                .withIntegerColumn(minutesColumn, "minute")
                .withStringColumn(routeNameColumn, "nameRoute")
                .withData(dispatcherDao.getTripsNow())
                .build();
    }

    public void sendTrip(ActionEvent actionEvent) {
        TripsNowView tripsNowView = simpleTripsTable.getSelectedRow();
        if (tripsNowView != null) {
            try {
                dispatcherDao.sendTrip(tripsNowView.getCodTrip());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Отправка рейса");
                alert.setContentText("Рейс успешно отправлен");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Отправка рейса");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } finally {
                simpleTripsTable.cleanTable();
                simpleTripsTable.setElementList(dispatcherDao.getTripsNow());
            }
        }
    }

    private String getDayOfWeek(DayOfWeek dayOfWeek) {
        String result;
        switch (dayOfWeek.getValue()) {
            case 1:
                result = "понедельник";
                break;
            case 2:
                result = "вторник";
                break;
            case 3:
                result = "среда";
                break;
            case 4:
                result = "четверг";
                break;
            case 5:
                result = "пятница";
                break;
            case 6:
                result = "суббота";
                break;
            case 7:
                result = "воскресенье";
                break;
            default:
                result = "unknown";
                break;
        }
        return result;
    }
}
