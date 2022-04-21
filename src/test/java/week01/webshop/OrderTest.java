package week01.webshop;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void createOrderTest() {
        Order order = new Order(2, 1, 3,
                LocalDateTime.of(LocalDate.of(2021, 12, 1), LocalTime.of(11, 11)));

        assertEquals(2, order.getId());
        assertEquals(1, order.getProductId());
        assertEquals(3, order.getAmount());
        assertEquals(LocalDateTime.parse("2021-12-01T11:11"), order.getOrderTime());
    }
}