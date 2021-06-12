package ru.alexksysx.dispatcher;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DispatcherDao {
    private JdbcTemplate jdbcTemplate;

    public DispatcherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TripsNowView> getTripsNow() {
        String sql = "select * from trip_now";
        return jdbcTemplate.query(sql, new TripsNowMapper());
    }

    public void sendTrip(Long codTrip) throws Exception {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("send_trip");
        SqlParameterSource in = new MapSqlParameterSource().addValue("cod_t", codTrip);
        Map<String, Object> out = call.execute(in);
        BigDecimal err = (BigDecimal) out.get("ERR");
        if(err.longValue() != 0)
            throw new Exception("Ошибка при отправке рейса");
    }
}
