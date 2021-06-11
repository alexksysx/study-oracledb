package ru.alexksysx.cashier;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.alexksysx.mappers.PointMapper;
import ru.alexksysx.objects.Point;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class CashierDao {
    private JdbcTemplate jdbcTemplate;

    public CashierDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Point> getAllPoints() {
        String sql = "select * from points";
        return jdbcTemplate.query(sql, new PointMapper());
    }

    public List<PointsTripsView> getTripsByCodPoint(Long codPoint) {
        String sql = "select * from points_trips where cod_point = ? and free_places > 0";
        return jdbcTemplate.query(sql, new PointsTripsMapper(), codPoint);
    }

    public String saleTickets(Long codTrip, Long codPoint, Integer ticketsNumber) throws Exception {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sale");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("cod_r", codTrip)
                .addValue("cod_p", codPoint)
                .addValue("n_ticket", ticketsNumber);
        Map<String, Object> ans = call.execute(in);
        BigDecimal error = (BigDecimal) ans.get("ERR");
        if (error.longValue() != 0)
            throw new Exception("Нет такого количества билетов");
        BigDecimal price = (BigDecimal) ans.get("S");
        return price.toString();
    }
}
