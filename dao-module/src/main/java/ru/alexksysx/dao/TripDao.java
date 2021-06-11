package ru.alexksysx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.alexksysx.mappers.TripMapper;
import ru.alexksysx.objects.Trip;

import java.sql.PreparedStatement;
import java.util.List;

public class TripDao {
    private JdbcTemplate jdbcTemplate;

    public TripDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Trip> getTripsByCodRoute(Long codRoute) {
        String sql = "select t.COD_TRIP, t.WEEK_DAY, t.HOUR, t.MINUTE, t.TICKETS, t.COD_ROUTE, t.COD_BUS, b.BUS_NUMBER from trips t " +
                "join buses b on b.cod_bus = t.cod_bus " +
                "where cod_route = ?";
        return jdbcTemplate.query(sql, new TripMapper(), codRoute);
    }

    public boolean deleteOneById(Long id) {
        String sql = "delete from trips where cod_trip = ?";
        int update = jdbcTemplate.update(sql, id);
        return update > 0;
    }

    public boolean updateOne(Trip object) {
        String sql = "update trips set cod_bus = ?, cod_route = ?, tickets = ?, " +
                "minute = ?, hour = ?, week_day = ? where cod_trip = ?";
        int update = jdbcTemplate.update(sql, object.getCodBus(), object.getCodRoute(), object.getTickets(),
                object.getMinute(), object.getHour(), object.getWeekDay(), object.getCodTrip());
        return update > 0;
    }

    public Trip createOne(Trip object) {
        String sql = "insert into trips(week_day, hour, minute, tickets, cod_route, cod_bus) values(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"cod_trip"});
            statement.setInt(1, object.getWeekDay());
            statement.setInt(2, object.getHour());
            statement.setInt(3, object.getMinute());
            statement.setInt(4, object.getTickets());
            statement.setLong(5, object.getCodRoute());
            statement.setLong(6, object.getCodBus());
            return statement;
        }, keyHolder);
        Long key = keyHolder.getKey().longValue();
        object.setCodTrip(key);
        return object;
    }
}
