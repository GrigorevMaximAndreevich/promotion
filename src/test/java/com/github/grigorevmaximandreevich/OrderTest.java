package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void order_shouldBeCreatedCorrectly() {
        LocalDateTime time = LocalDateTime.of(2021, 2, 9, 8, 19, 22);
        Order order = new Order(time, "Recovery", new BigDecimal("11340"));

        assertEquals(time, order.getOrderTime());
        assertEquals("Recovery", order.getCompanyName());
        assertEquals(new BigDecimal("11340"), order.getQuantity());
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        LocalDateTime time = LocalDateTime.of(2021, 2, 9, 8, 19, 22);
        Order order1 = new Order(time, "Recovery", new BigDecimal("11340"));
        Order order2 = new Order(time, "Recovery", new BigDecimal("11340"));
        Order order3 = new Order(time, "Atomic", new BigDecimal("12500"));

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1, order3);
    }
    @Test
    void orderConstructor_shouldRejectNullOrderTime() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(null, "Company", new BigDecimal("1000"))
        );
    }

    @Test
    void orderConstructor_shouldRejectNullCompanyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), null, new BigDecimal("1000"))
        );
    }

    @Test
    void orderConstructor_shouldRejectEmptyCompanyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "", new BigDecimal("1000"))
        );
    }

    @Test
    void orderConstructor_shouldRejectNullQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "Company", null)
        );
    }

    @Test
    void orderConstructor_ShouldRejectZeroOrNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "Company", BigDecimal.ZERO)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "Company", new BigDecimal("-100"))
        );
    }
}
