package pro.sky.recommendation_service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

}
