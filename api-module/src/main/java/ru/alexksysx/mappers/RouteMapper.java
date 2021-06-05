package ru.alexksysx.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.Route;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteMapper implements RowMapper<Route> {
    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        Route route = new Route();
        route.setCodRoute(rs.getLong("cod_route"));
        route.setNameRoute(rs.getString("name_route"));
        return route;
    }
}
