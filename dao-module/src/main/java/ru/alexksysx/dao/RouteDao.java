package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.RouteMapper;
import ru.alexksysx.objects.Route;

import java.sql.PreparedStatement;
import java.util.List;

public class RouteDao implements ObjectDao<Route>{
    private JdbcTemplate jdbcTemplate;

    public RouteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Route getOneById(Long id) {
        String sql = "select * from routes where cod_route = ?";
        return jdbcTemplate.queryForObject(sql, new RouteMapper(), id);
    }

    @Override
    public List<Route> getAll() {
        String sql = "select * from routes";
        return jdbcTemplate.query(sql, new RouteMapper());
    }

    @Override
    public boolean deleteOneById(Long id) {
        String sql = "delete from routes where COD_ROUTE = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    @Override
    public boolean updateOne(Route object) {
        String sql = "update routes set name_route = ? where cod_route = ?";
        int update = jdbcTemplate.update(sql, object.getNameRoute(), object.getCodRoute());
        return update > 0;
    }

    @Override
    public Route createOne(Route object) {
        String sql = "insert into routes(name_route) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"cod_route"});
            statement.setString(1, object.getNameRoute());
            return statement;
        }, keyHolder);
        Long key = keyHolder.getKey().longValue();
        object.setCodRoute(key);
        return object;
    }
}
