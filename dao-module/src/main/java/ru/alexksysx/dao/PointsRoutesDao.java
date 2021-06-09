package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.alexksysx.mappers.PointMapper;
import ru.alexksysx.objects.Point;

import java.util.List;

public class PointsRoutesDao {
    private JdbcTemplate jdbcTemplate;

    public PointsRoutesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addPointToRoute(Long routeId, Long pointId) {
        String sql = "insert into points_routes(cod_route, cod_point) values(?, ?)";
        int update = jdbcTemplate.update(sql, routeId, pointId);
        return update > 0;
    }

    public boolean removePointFromRoute(Long routeId, Long pointId) {
        String sql = "delete from points_routes where cod_route = ? and cod_point = ?";
        int update = jdbcTemplate.update(sql, routeId, pointId);
        return update > 0;
    }

    public List<Point> getPointsFromRoute(Long routeId) {
        String sql = "select * from points where cod_point in " +
                "(select cod_point from points_routes where cod_route = ?)";
        return jdbcTemplate.query(sql, new PointMapper(), routeId);
    }
}
