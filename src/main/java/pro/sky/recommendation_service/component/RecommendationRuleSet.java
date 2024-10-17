package pro.sky.recommendation_service.component;

import pro.sky.recommendation_service.entities.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(UUID user);
}
