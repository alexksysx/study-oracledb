package ru.alexksysx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class Controller {
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private JdbcTemplate jdbcTemplate = Main.getJdbcTemplate();

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TextField idInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private TextField emailInput;

    @FXML
    private Button insertButton;

    @FXML
    private void insertRow() {
        Integer id;
        try {
            id = Integer.parseInt(idInput.getText());
        } catch (NumberFormatException e) {
            idInput.requestFocus();
            createAlert("Здесь нужно ввести число", "Ошибка");
            return;
        }
        String login = loginInput.getText();
        String password = passwordInput.getText();
        String email = emailInput.getText();
        int update = jdbcTemplate.update("insert into test_user.users values (?, ?, ?, ?)", id, login, password, email);
        if (update > 0) {
            User user = new User(id, login, password, email);
            usersData.add(user);
        }

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
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        // заполняем таблицу данными
        tableUsers.setItems(usersData);
    }

    // подготавливаем данные для таблицы
    private void initData() {
        List<User> users = jdbcTemplate.query("select * from test_user.users", new UserRowMapper());
        usersData.addAll(users);
    }
}