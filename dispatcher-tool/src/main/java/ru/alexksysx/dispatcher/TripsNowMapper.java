package ru.alexksysx.dispatcher;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripsNowMapper implements RowMapper<TripsNowView> {
    @Override
    public TripsNowView mapRow(ResultSet rs, int i) throws SQLException {
        TripsNowView tripsNowView = new TripsNowView();
        tripsNowView.setCodTrip(rs.getLong("cod_trip"));
        tripsNowView.setNameRoute(rs.getString("name_route"));
        tripsNowView.setWeekDay(rs.getInt("week_day"));
        tripsNowView.setHour(rs.getInt("hour"));
        tripsNowView.setMinute(rs.getInt("minute"));
        tripsNowView.setTickets(rs.getInt("tickets"));
        return tripsNowView;
    }
}
