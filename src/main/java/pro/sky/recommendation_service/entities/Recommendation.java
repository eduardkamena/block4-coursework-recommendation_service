package pro.sky.recommendation_service.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Recommendation {
    UUID id;
    String productName;
    String productPreview;
}
