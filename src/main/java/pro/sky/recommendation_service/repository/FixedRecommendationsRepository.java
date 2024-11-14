package pro.sky.recommendation_service.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class FixedRecommendationsRepository {

    private final Logger logger = LoggerFactory.getLogger(FixedRecommendationsRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FixedRecommendationsRepository(
            @Qualifier("recommendationsJdbcTemplate")
            JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Join SQL запрос в БД на сумму транзакций пополнения или списания (transactionType) по продукту (productsType)
    @Cacheable(cacheNames = "fixedRecommendations", key = "#root.methodName + #user_id.toString() + #productsType + #transactionType")
    public int getTransactionAmount(UUID user_id, String productsType, String transactionType) {
        String sql = "SELECT SUM(t.AMOUNT) AS transactions_amount " +
                "           FROM TRANSACTIONS t " +
                "           INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "           WHERE p.TYPE = '" + productsType + "' " +
                "           AND t.TYPE = '" + transactionType + "' " +
                "           AND t.USER_ID = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, user_id);

        logger.info("Executing a SQL query for user transactions amount " +
                "for user_id: {} with product type: {} and transaction type: {}", user_id, productsType, transactionType);
        return result != null ? result : 0;
    }

    // Запрос в БД на наличие/отсутствия продукта у пользователя
    @Cacheable(cacheNames = "fixedRecommendations", key = "#root.methodName + #user_id.toString() + #productsType")
    public boolean isProductExists(UUID user_id, String productsType) {
        String sql = "SELECT COUNT(*) " +
                "           FROM TRANSACTIONS t " +
                "           INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                "           WHERE p.TYPE = '" + productsType + "' " +
                "           AND t.USER_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user_id);

        logger.info("Executing a SQL query for the presence of a user product in the database " +
                "for user_id: {} and product type: {}", user_id, productsType);
        return count != null && count > 0;
    }

    // Запрос в БД на наличие/отсутствия пользователя
    @Caching( cacheable = {
            @Cacheable(cacheNames = "dynamicRecommendations", key = "#root.methodName + #user_id.toString()"),
            @Cacheable(cacheNames = "fixedRecommendations", key = "#root.methodName + #user_id.toString()")
    })
    public boolean isUserExists(UUID user_id) {
        String sql = "SELECT COUNT(*) FROM USERS u WHERE u.ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user_id);

        logger.info("Executing a SQL query \"isUserExists\" in the database for user_id: {}", user_id);
        return count != null && count > 0;
    }

}
