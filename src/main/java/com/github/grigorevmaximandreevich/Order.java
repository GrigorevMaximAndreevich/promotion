package com.github.grigorevmaximandreevich;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private final LocalDateTime orderTime;

    private final String companyName;

    private final BigDecimal quantity;

    public Order(LocalDateTime orderTime, String companyName, BigDecimal quantity) {
        if (orderTime == null) {
            throw new IllegalArgumentException("Время заказа не может быть null");
        }
        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("Название компании не может быть null или пустым");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Количество не может быть null");
        }
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным, получено: " + quantity);
        }
        this.orderTime = orderTime;
        this.companyName = companyName;
        this.quantity = quantity;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderTime, order.orderTime) &&
                Objects.equals(companyName, order.companyName) &&
                Objects.equals(quantity, order.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTime, companyName, quantity);
    }
}
