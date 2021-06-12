package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.ModelMapper;
import ru.alexksysx.objects.Model;

import java.sql.PreparedStatement;
import java.util.List;

public class ModelsDao {
    private JdbcTemplate jdbcTemplate;
    public ModelsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Model> getAll() {
        String sql = "select * from models";
        return jdbcTemplate.query(sql, new ModelMapper());
    }

    public boolean deleteOneById(Long id) {
        String sql = "delete from models where cod_model = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    public boolean updateOne(Model object) {
        String sql = "update models set name_model = ?, places = ?, class = ? where cod_model = ?";
        int update = jdbcTemplate.update(sql, new ModelMapper(), object.getNameModel(), object.getPlaces(),
                object.getModelClass(), object.getCodModel());
        return update > 0;
    }

    public Model createOne(Model object) {
        String sql = "insert into models(name_model, places, class) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"cod_model"});
            statement.setString(1, object.getNameModel());
            statement.setInt(2, object.getPlaces());
            statement.setLong(3, object.getModelClass());
            return statement;
        }, keyHolder);
        Long key = keyHolder.getKey().longValue();
        object.setCodModel(key);
        return object;
    }
}

