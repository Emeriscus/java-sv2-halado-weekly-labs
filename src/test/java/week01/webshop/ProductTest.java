package week01.webshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void createProductTest() {
        Product product = new Product(3, "Milk", 300, 10);

        assertEquals(3, product.getId());
        assertEquals("Milk", product.getName());
        assertEquals(300, product.getPrice());
        assertEquals(10, product.getStock());
    }
}