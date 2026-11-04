package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void order_shouldBeCreatedCorrectly() {
        LocalDateTime time = LocalDateTime.of(2021, 2, 9, 8, 19, 22);
        Order order = new Order(time, "Recovery", 11340);

        assertEquals(time, order.getOrderTime());
        assertEquals("Recovery", order.getCompanyName());
        assertEquals(11340, order.getQuantity());
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        LocalDateTime time = LocalDateTime.of(2021, 2, 9, 8, 19, 22);
        Order order1 = new Order(time, "Recovery", 11340);
        Order order2 = new Order(time, "Recovery", 11340);
        Order order3 = new Order(time, "Atomic", 12500);

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1, order3);
    }
    @Test
    void orderConstructor_shouldRejectNullOrderTime() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(null, "Company", 1000)
        );
    }

    @Test
    void orderConstructor_shouldRejectNullCompanyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), null, 1000)
        );
    }

    @Test
    void orderConstructor_shouldRejectEmptyCompanyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "", 1000)
        );
    }

    @Test
    void orderConstructor_ShouldRejectZeroOrNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "Company", 0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Order(LocalDateTime.now(), "Company", -100)
        );
    }
}
