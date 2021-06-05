package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.PointMapper;
import ru.alexksysx.objects.Point;

import java.sql.PreparedStatement;
import java.util.List;

public class PointsDao implements ObjectDao<Point>{

    private JdbcTemplate jdbcTemplate;

    public PointsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Point getOneById(Long id) {
        String sql = "select * from points where cod_point = ?";
        return jdbcTemplate.queryForObject(sql, new PointMapper(), id);
    }

    @Override
    public List<Point> getAll() {
        String sql = "select * from points";
        return jdbcTemplate.query(sql, new PointMapper());
    }

    @Override
    public boolean deleteOneById(Long id) {
        String sql = "delete from points where cod_point = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    @Override
    public boolean updateOne(Point object) {
        String sql = "update points set name_point = ?, distance = ? where cod_point = ?";
        int update = jdbcTemplate.update(sql, object.getNamePoint(), object.getDistance(), object.getCodPoint());
        return update > 0;
    }

    @Override
    public Point createOne(Point object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into points(name_point, distance) values (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"cod_point"});
            statement.setString(1, object.getNamePoint());
            statement.setLong(2, object.getDistance());
            return statement;
        }, keyHolder);
        Long codPoint = keyHolder.getKey().longValue();
        object.setCodPoint(codPoint);
        return object;
    }
}
