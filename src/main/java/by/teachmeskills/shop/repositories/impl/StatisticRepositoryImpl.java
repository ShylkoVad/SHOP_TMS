package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.StatisticEntity;
import by.teachmeskills.shop.repositories.StatisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class StatisticRepositoryImpl implements  StatisticRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_STATISTIC_DATA_QUERY = "INSERT INTO statistic (description) VALUES (?)";
    private static final String GET_ALL_STATISTICS_QUERY = "SELECT * FROM statistic";
    private static final String UPDATE_STATISTIC_DESCRIPTION_QUERY = "UPDATE statistic SET description = ? WHERE id = ?";
    private static final String DELETE_STATISTIC_DATA_QUERY = "DELETE FROM statistic WHERE id = ?";

    public StatisticRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StatisticEntity create(StatisticEntity entity) {
        int result = jdbcTemplate.update(ADD_STATISTIC_DATA_QUERY, entity.getDescription());

        if (result <= 0) {
            log.info("No rows where updated.");
        }

        return entity;
    }

    @Override
    public List<StatisticEntity> read() {
        return jdbcTemplate.query(GET_ALL_STATISTICS_QUERY, (rs, rowNum) -> StatisticEntity.builder()
                .id(rs.getInt("id"))
                .description(rs.getString("description"))
                .build());
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) {
        return jdbcTemplate.execute(UPDATE_STATISTIC_DESCRIPTION_QUERY, (PreparedStatementCallback<StatisticEntity>) ps -> {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getDescription());
            ps.execute();
            return entity;
        });
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_STATISTIC_DATA_QUERY, id);
    }
}
