package ru.alexksysx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        stage.setTitle("Users List");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static JdbcTemplate getJdbcTemplate() {
//        DataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@localhost:1527:orcl", "system", "oracle");
        DataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@localhost:1527:orcl", "test_user", "test_user");
        return new JdbcTemplate(ds);
    }
}
