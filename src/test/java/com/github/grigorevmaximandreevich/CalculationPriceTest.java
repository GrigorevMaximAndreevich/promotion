package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationPriceTest {

    private Order createOrder(String company, String quantity, int hour) {
        return new Order(LocalDateTime.of(
                2021, 2, 9, hour, 0),
                company,
                new BigDecimal(quantity));
    }

    @Test
    void calculatePrice_shouldApplyDecreasingDiscountAndSumForSameCompany() {
        CalculationPrice calculator = new CalculationPrice(
                new BigDecimal("0.50"),
                new BigDecimal("10"),
                new BigDecimal("0.05")
        );

        List<Order> orders = List.of(
                createOrder("Recovery", "1000", 8),
                createOrder("Power Engineer", "1000", 9),
                createOrder("Power Engineer", "1000", 10)
        );

        Map<String, BigDecimal> result = calculator.calculatePrice(orders);

        assertEquals(new BigDecimal("5000.00"), result.get("Recovery"));
        assertEquals(new BigDecimal("11500.00"), result.get("Power Engineer"));
    }

    @Test
    void calculatePrice_shouldNotAllowNegativeDiscount() {
        CalculationPrice calculator = new CalculationPrice(
                new BigDecimal("0.10"), new BigDecimal("10"), new BigDecimal("0.05")
        );

        List<Order> orders = java.util.stream.IntStream.range(0, 5)
                .mapToObj(i -> createOrder("Company" + i, "1000", i + 1))
                .toList();

        Map<String, BigDecimal> result = calculator.calculatePrice(orders);

        assertEquals(new BigDecimal("10000.00"), result.get("Company3"));
        assertEquals(new BigDecimal("10000.00"), result.get("Company4"));
    }
}
