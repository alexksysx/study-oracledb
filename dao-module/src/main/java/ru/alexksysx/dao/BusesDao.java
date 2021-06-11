package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.BusMapper;
import ru.alexksysx.objects.Bus;

import java.sql.PreparedStatement;
import java.util.List;

public class BusesDao {
    private JdbcTemplate jdbcTemplate;

    public BusesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Bus> getAll() {
        String sql = "select * from buses";
        return jdbcTemplate.query(sql, new BusMapper());
    }

    public boolean deleteOneById(Long id) {
        String sql = "delete from buses where cod_bus = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    public boolean updateOne(Bus object) {
        String sql = "update buses set bus_number = ?, cod_model = ? where cod_bus = ?";
        int update = jdbcTemplate.update(sql, object.getBusNumber(), object.getCodModel(), object.getCodBus());
        return update > 0;
    }

    public Bus createOne(Bus object) {
        String sql = "insert into buses(bus_number, cod_model) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"cod_bus"});
            statement.setString(1, object.getBusNumber());
            statement.setLong(2, object.getCodModel());
            return statement;
        }, keyHolder);
        Long key = keyHolder.getKey().longValue();
        object.setCodBus(key);
        return object;
    }

    public List<Bus> getBusesByModel(Long codModel) {
        String sql = "select * from buses where cod_model = ?";
        return jdbcTemplate.query(sql, new BusMapper(), codModel);
    }
}
