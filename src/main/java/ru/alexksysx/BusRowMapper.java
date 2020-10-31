package ru.alexksysx;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusRowMapper implements RowMapper<Bus> {
    @Override
    public Bus mapRow(ResultSet resultSet, int i) throws SQLException {
        Bus bus = new Bus();
        bus.setId(resultSet.getInt("id"));
        bus.setModel(resultSet.getString("bus_model"));
        bus.setName(resultSet.getString("bus_name"));
        bus.setNumber(resultSet.getString("bus_number"));
        return bus;
    }
}
