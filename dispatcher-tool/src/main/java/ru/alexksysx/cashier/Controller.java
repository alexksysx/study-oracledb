package ru.alexksysx.cashier;

import javafx.fxml.FXML;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class Controller {
    private JdbcTemplate jdbcTemplate;

    @FXML
    public void initialize() {
        DataSource ds = new DriverManagerDataSource(
                "jdbc:oracle:thin:@192.168.0.15:1527:orcl",
                "test_user", "test_user");
        jdbcTemplate = new JdbcTemplate(ds);
    }
}
