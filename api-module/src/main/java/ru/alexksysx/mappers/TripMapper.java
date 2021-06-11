package ru.alexksysx.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.Trip;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripMapper implements RowMapper<Trip> {
    @Override
    public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trip trip = new Trip();
        trip.setCodBus(rs.getLong("cod_bus"));
        trip.setHour(rs.getInt("hour"));
        trip.setMinute(rs.getInt("minute"));
        trip.setCodTrip(rs.getLong("cod_trip"));
        trip.setTickets(rs.getInt("tickets"));
        trip.setWeekDay(rs.getInt("week_day"));
        trip.setCodRoute(rs.getLong("cod_route"));
        trip.setBusNumber(rs.getString("bus_number"));
        return trip;
    }
}
