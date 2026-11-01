package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationPrice {

    private final int startDiscount;
    private final BigDecimal pricePerKg;
    private final int discountStep;

    public CalculationPrice(int startDiscount, BigDecimal pricePerKg, int discountStep) {
        if (startDiscount < 0 || startDiscount > 100) {
            throw new IllegalArgumentException(
                    "Стартовая скидка должна быть в диапазоне от 0 до 100, получено: " + startDiscount
            );
        }
        if (discountStep < 0 || discountStep > 100) {
            throw new IllegalArgumentException(
                    "Шаг скидки должен быть в диапазоне от 0 до 100, получено: " + discountStep
            );
        }
        if (pricePerKg == null || pricePerKg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Цена за кг не может быть отрицательной или null"
            );
        }

        this.startDiscount = startDiscount;
        this.pricePerKg = pricePerKg;
        this.discountStep = discountStep;
    }

    public Map<String, BigDecimal> calculatePrice(List<Order> orders) {
        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderTime))
                .toList();

        Map<String, BigDecimal> companyOrdersPrices = new TreeMap<>();

        int currentDiscount = startDiscount;

        for (Order order : sortedOrders) {
            currentDiscount = Math.max(currentDiscount, 0);

            BigDecimal discountFraction = BigDecimal.valueOf(currentDiscount)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            BigDecimal orderPrice = order.getQuantity()
                    .multiply(pricePerKg)
                    .multiply(BigDecimal.ONE.subtract(discountFraction))
                    .setScale(2, RoundingMode.HALF_UP);

            if (companyOrdersPrices.containsKey(order.getCompanyName())) {
                BigDecimal resultPrice = orderPrice.add(companyOrdersPrices.get(order.getCompanyName()));
                companyOrdersPrices.put(order.getCompanyName(), resultPrice);
            } else {
                companyOrdersPrices.put(order.getCompanyName(), orderPrice);
            }
            currentDiscount -= discountStep;
        }
        return companyOrdersPrices;
    }
}
