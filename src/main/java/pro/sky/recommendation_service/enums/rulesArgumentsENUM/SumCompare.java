package pro.sky.recommendation_service.enums.rulesArgumentsENUM;

import lombok.Getter;

/**
 * Перечисление возможных значений для аргумента правил динамической рекомендации
 */
@Getter
public enum SumCompare {

    A(1_000),
    B(50_000),
    C(100_000);

    private final int sumVal;

    SumCompare(int sumVal) {
        this.sumVal = sumVal;
    }

}
