package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.dto.RecommendationsDTO;
import pro.sky.recommendation_service.entity.Recommendations;
import pro.sky.recommendation_service.exception.AppError;
import pro.sky.recommendation_service.service.DynamicRulesRecommendationsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rule")
@Tag(
        name = "Контроллер динамических правил рекомендаций",
        description = "Выполняет действия с динамическими правилами рекомендаций")
public class DynamicRulesRecommendationsController {

    private final DynamicRulesRecommendationsService dynamicRulesRecommendationsService;

    public DynamicRulesRecommendationsController(DynamicRulesRecommendationsService dynamicRulesRecommendationsService) {
        this.dynamicRulesRecommendationsService = dynamicRulesRecommendationsService;
    }

    @PostMapping
    public ResponseEntity<Recommendations> createRecommendation(
            @Parameter(description = "Создание динамического правила рекомендации")
            @RequestBody RecommendationsDTO recommendation) {

        Recommendations createdRecommendation = dynamicRulesRecommendationsService.createRecommendation(recommendation);

        return ResponseEntity.ok(createdRecommendation);
    }

    @GetMapping(path = "/{rule_id}")
    public ResponseEntity<Optional<RecommendationsDTO>> getRule(
            @Parameter(description = "Идентификатор динамического правила рекомендации")
            @PathVariable UUID rule_id) {

        Optional<RecommendationsDTO> foundRecommendation = dynamicRulesRecommendationsService.getRule(rule_id);

        return ResponseEntity.ok(foundRecommendation);
    }

    @DeleteMapping(path = "/{rule_id}")
    public ResponseEntity<Object> deleteRule(
            @Parameter(description = "Идентификатор динамического правила рекомендации")
            @PathVariable UUID rule_id) {

        dynamicRulesRecommendationsService.deleteRule(rule_id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new AppError(HttpStatus.NO_CONTENT.value(),
                        "No Content"));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<RecommendationsDTO>> getAllRules() {

        List<RecommendationsDTO> foundAllRecommendations = dynamicRulesRecommendationsService.getAllRules();

        return ResponseEntity.ok(foundAllRecommendations);
    }

}