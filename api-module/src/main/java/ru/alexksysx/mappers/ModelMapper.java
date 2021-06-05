package ru.alexksysx.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMapper implements RowMapper<Model> {
    @Override
    public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
        Model model = new Model();
        model.setCodModel(rs.getLong("cod_model"));
        model.setModelClass(rs.getLong("class"));
        model.setNameModel(rs.getString("name_model"));
        model.setPlaces(rs.getInt("places"));
        return model;
    }
}
