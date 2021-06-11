package ru.alexksysx.cashier;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointsTripsMapper implements RowMapper<PointsTripsView> {
    @Override
    public PointsTripsView mapRow(ResultSet rs, int i) throws SQLException {
        PointsTripsView pointsTripsView = new PointsTripsView();
        pointsTripsView.setCodPoint(rs.getLong("cod_point"));
        pointsTripsView.setCodTrip(rs.getLong("cod_trip"));
        pointsTripsView.setNameRoute(rs.getString("name_route"));
        pointsTripsView.setWeekDay(rs.getInt("week_day"));
        pointsTripsView.setTime(rs.getString("time"));
        pointsTripsView.setModelClass(rs.getLong("class"));
        pointsTripsView.setFreePlaces(rs.getInt("free_places"));
        return pointsTripsView;
    }
}
