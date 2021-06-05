package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.KmPriceMapper;
import ru.alexksysx.objects.KmPrice;

import java.sql.PreparedStatement;
import java.util.List;

public class PriceDao implements ObjectDao<KmPrice>{
    private final JdbcTemplate jdbcTemplate;

    public PriceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public KmPrice getOneById(Long id) {
        String sql = "select * from km_price where class = ?";
        return jdbcTemplate.queryForObject(sql, new KmPriceMapper(), id);
    }

    @Override
    public List<KmPrice> getAll() {
        String sql = "select * from km_price";
        return jdbcTemplate.query(sql, new KmPriceMapper());
    }

    @Override
    public boolean deleteOneById(Long id) {
        String sql = "delete from km_price where class = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean updateOne(KmPrice object) {
        String sql = "update km_price where class = ? and price = ?";
        return jdbcTemplate.update(sql, object.getModelClass(), object.getPrice()) > 1;
    }

    @Override
    public KmPrice createOne(KmPrice object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into km_price(class, price) values(?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, object.getModelClass());
            statement.setDouble(2, object.getPrice());
            return statement;
        }, keyHolder);
        Long classValue = keyHolder.getKey().longValue();
        object.setModelClass(classValue);
        return object;
    }
}
