package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationPrice {

    private final BigDecimal startDiscount;
    private final BigDecimal pricePerKg;
    private final BigDecimal discountStep;

    public CalculationPrice(BigDecimal startDiscount, BigDecimal pricePerKg, BigDecimal discountStep) {
        this.startDiscount = startDiscount;
        this.pricePerKg = pricePerKg;
        this.discountStep = discountStep;
    }

    public Map<String, BigDecimal> calculatePrice(List<Order> orders) {
        Map<String, BigDecimal> companyOrdersPrices = new TreeMap<>();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            BigDecimal currentDiscount = startDiscount.subtract(discountStep.multiply(BigDecimal.valueOf(i)));
            currentDiscount = currentDiscount.max(BigDecimal.ZERO);

            BigDecimal orderPrice = order.getQuantity()
                    .multiply(pricePerKg)
                    .multiply(BigDecimal.ONE.subtract(currentDiscount))
                    .setScale(2, RoundingMode.HALF_UP);

            if (companyOrdersPrices.containsKey(order.getCompanyName())) {
                BigDecimal resultPrice = orderPrice.add(companyOrdersPrices.get(order.getCompanyName()));
                companyOrdersPrices.put(order.getCompanyName(), resultPrice);
            } else {
                companyOrdersPrices.put(order.getCompanyName(), orderPrice);
            }
        }
        return companyOrdersPrices;
    }
}
