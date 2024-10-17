package pro.sky.recommendation_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendation_service.service.RecommendationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecommendationsController {

    private final RecommendationService recommendationService;

}
