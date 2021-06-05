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
        String sql = "select * from km_prices where class = ?";
        return jdbcTemplate.queryForObject(sql, new KmPriceMapper(), id);
    }

    @Override
    public List<KmPrice> getAll() {
        String sql = "select * from km_prices";
        return jdbcTemplate.query(sql, new KmPriceMapper());
    }

    @Override
    public boolean deleteOneById(Long id) {
        String sql = "delete from km_prices where class = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean updateOne(KmPrice object) {
        String sql = "update km_prices set price = ? where class = ?";
        int update = jdbcTemplate.update(sql, object.getPrice(), object.getModelClass());
        return update > 0;
    }

    @Override
    public KmPrice createOne(KmPrice object) {
        String sql = "insert into km_prices(class, price) values(?, ?)";
        int update = jdbcTemplate.update(sql, object.getModelClass(), object.getPrice());
        if (update == 1)
            return object;
        return null;
    }
}
