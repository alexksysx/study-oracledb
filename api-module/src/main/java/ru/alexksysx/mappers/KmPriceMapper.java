package ru.alexksysx.mappers;


import org.springframework.jdbc.core.RowMapper;
import ru.alexksysx.objects.KmPrice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KmPriceMapper implements RowMapper<KmPrice> {
    @Override
    public KmPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
        KmPrice kmPrice = new KmPrice();
        kmPrice.setModelClass(rs.getLong("class"));
        kmPrice.setPrice(rs.getDouble("price"));
        return kmPrice;
    }
}
