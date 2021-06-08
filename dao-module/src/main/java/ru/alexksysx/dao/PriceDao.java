package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.alexksysx.mappers.KmPriceMapper;
import ru.alexksysx.objects.KmPrice;

import java.util.List;

public class PriceDao {
    private final JdbcTemplate jdbcTemplate;

    public PriceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KmPrice> getAll() {
        String sql = "select * from km_prices";
        return jdbcTemplate.query(sql, new KmPriceMapper());
    }

    public boolean deleteOneById(Long id) {
        String sql = "delete from km_prices where class = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean updateOne(KmPrice object) {
        String sql = "update km_prices set price = ? where class = ?";
        int update = jdbcTemplate.update(sql, object.getPrice(), object.getModelClass());
        return update > 0;
    }

    public KmPrice createOne(KmPrice object) {
        String sql = "insert into km_prices(class, price) values(?, ?)";
        int update = jdbcTemplate.update(sql, object.getModelClass(), object.getPrice());
        if (update == 1)
            return object;
        return null;
    }
}
