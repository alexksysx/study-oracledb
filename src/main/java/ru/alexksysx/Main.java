package ru.alexksysx;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@localhost:1527:orcl", "system", "oracle");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        Integer a = jdbcTemplate.queryForObject("select 2 from dual", Integer.class);
        System.out.println(a);
    }
}
