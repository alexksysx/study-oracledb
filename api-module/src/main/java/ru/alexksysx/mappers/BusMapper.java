package ru.alexksysx.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.Bus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusMapper implements RowMapper<Bus> {
    @Override
    public Bus mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bus bus = new Bus();
        bus.setBusNumber(rs.getString("bus_number"));
        bus.setCodBus(rs.getLong("cod_bus"));
        bus.setCodModel(rs.getLong("cod_model"));
        return bus;
    }
}
