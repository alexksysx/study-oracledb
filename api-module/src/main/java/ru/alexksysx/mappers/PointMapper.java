package ru.alexksysx.mappers;


import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.Point;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointMapper implements RowMapper<Point> {
    @Override
    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
        Point point = new Point();
        point.setCodPoint(rs.getLong("cod_point"));
        point.setDistance(rs.getLong("distance"));
        point.setNamePoint(rs.getString("name_point"));
        return point;
    }
}
