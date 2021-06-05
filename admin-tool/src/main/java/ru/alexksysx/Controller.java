package ru.alexksysx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.alexksysx.simplefx.SimpleFXTable;

import java.util.List;


public class Controller {
//    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private JdbcTemplate jdbcTemplate = Main.getJdbcTemplate();

    private SimpleFXTable<User> simpleFXTable;

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
        int update;
        try {
            update = jdbcTemplate.update("insert into users values (?, ?, ?, ?)", id, login, password, email);
        } catch (DuplicateKeyException e) {
            createAlert("Запись с таким ключём уже существует", "Ошибка");
            return;
        }
        if (update > 0) {
            User user = new User(id, login, password, email);
            simpleFXTable.addRow(user);
        }
        idInput.clear();
        loginInput.clear();
        passwordInput.clear();
        emailInput.clear();
    }

    private void createAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


//    @FXML
//    private void initialize() {
//        initData();
//        // устанавливаем тип и значение которое должно хранится в колонке
//        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
//        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
//        // заполняем таблицу данными
//        tableUsers.setItems(usersData);
//        // делаем колонны изменяемыми
//        tableUsers.setEditable(true);
//        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//    }

    @FXML
    private void initialize() {
        List<User> users = jdbcTemplate.query("select * from users", new UserRowMapper());
        simpleFXTable = new SimpleFXTable.Builder<User>(tableUsers)
                .withIntegerColumn(idColumn, "id")
                .withStringColumn(loginColumn, "login")
                .withStringColumn(passwordColumn, "password")
                .withStringColumn(emailColumn, "email")
                .withData(users)
                .build();
        simpleFXTable.setEditable(true);
    }

    // подготавливаем данные для таблицы
//    private void initData() {
//        List<User> users = jdbcTemplate.query("select * from test_user.users", new UserRowMapper());
//        usersData.addAll(users);
//    }

    public void loginEditHandler(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        User user = userStringCellEditEvent.getTableView().getItems().get(userStringCellEditEvent.getTablePosition().getRow());
        user.setLogin(userStringCellEditEvent.getNewValue());
        if (!updateUser(user)) {
            user.setLogin(userStringCellEditEvent.getOldValue());
        }
    }

    public void emailEditHandler(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        User user = simpleFXTable.getSelectedRow();
        user.setEmail(userStringCellEditEvent.getNewValue());
        if (!updateUser(user)) {
            user.setEmail(userStringCellEditEvent.getOldValue());
            System.out.println("EDITED!");
        }
    }

    public void passwordEditHandler(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        User user = simpleFXTable.getSelectedRow();
        user.setPassword(userStringCellEditEvent.getNewValue());
        if (!updateUser(user)) {
            user.setPassword(userStringCellEditEvent.getOldValue());
        }
    }

    private boolean updateUser(User user) {
        int update = jdbcTemplate.update("update users set login = ?, password = ?, email = ? where id = ?",
                user.getLogin(), user.getPassword(), user.getEmail(), user.getId());
        return update > 0;
    }

    private boolean deleteRow(User user) {
        int delete = jdbcTemplate.update("delete from users where id = ?", user.getId());
        return delete > 0;
    }

    public void deleteRow(ActionEvent actionEvent) {
        int index = simpleFXTable.getSelectedRowIndex();
        if (index == -1)
            return;
        User user = simpleFXTable.getRowByIndex(index);
        if (deleteRow(user)) {
            simpleFXTable.removeRowByIndex(index);
        }
    }
}
