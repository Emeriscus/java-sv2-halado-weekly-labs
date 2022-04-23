package week01.webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebshopServiceTest {

    WebshopService webshopService;
    UserDao userDao;
    ProductDao productDao;
    OrderDao orderDao;

    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/webshop_emeriscus_test?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        userDao = new UserDao(dataSource);
        orderDao = new OrderDao(dataSource);
        productDao = new ProductDao(dataSource);

        webshopService = new WebshopService(userDao, orderDao, productDao);
//        webshopService.loadProductsFromFile(Path.of("src/test/resources/products.csv"));
        webshopService.saveUser("test@test.com", "dsfdfaDASF.88");
        webshopService.saveUser("othertest@othertest.com", "zujujFGTZJH.656");

        productDao.addProduct(new Product("Milk", 250, 15));
        productDao.addProduct(new Product("Wine", 700, 5));

    }

    @Test
    void saveUserTest() {
        webshopService.saveUser("onemoretest@test.com", "dsnnnfaD.656");
    }

    @Test
    void saveUserTestWithTakenEmailTest() {
        Exception ex = assertThrows(IllegalStateException.class,
                () -> webshopService.saveUser("test@test.com", "dsfdfaDASF.88"));

        assertEquals("The email is already taken: test@test.com", ex.getMessage());
    }

    @Test
    void logInWithValidUserTest() {
        webshopService.logIn("test@test.com", "dsfdfaDASF.88");
        assertTrue(webshopService.isLoggedIn());

        long id = userDao.getUserIdByEmail("test@test.com");
        assertEquals(id, webshopService.getCurrentUserId());
    }

    @Test
    void logInWithInValidUserTest() {
        Exception ex = assertThrows(IllegalStateException.class,
                () -> webshopService.logIn("tes@test.com", "dsfdfaDASF.88"));

        assertEquals("Cannot find email: tes@test.com", ex.getMessage());
        assertFalse(webshopService.isLoggedIn());
        assertEquals(0, webshopService.getCurrentUserId());

        webshopService.logIn("test@test.com", "dsfdfaDASF.89");
        assertFalse(webshopService.isLoggedIn());
        assertEquals(0, webshopService.getCurrentUserId());
    }

    @Test
    void addToCartTest() {
        webshopService.logIn("test@test.com", "dsfdfaDASF.88");

        webshopService.addToCart(1, 5);
        assertEquals(5, webshopService.getCart().get(1L));

        webshopService.addToCart(1, 3);
        assertEquals(8, webshopService.getCart().get(1L));

        webshopService.addToCart(2, 5);
        assertEquals(5, webshopService.getCart().get(2L));
    }

    @Test
    void addToCartInvalidQuantityTest() {
        webshopService.logIn("test@test.com", "dsfdfaDASF.88");

        webshopService.addToCart(1, 15);
        assertEquals(15, webshopService.getCart().get(1L));

        Exception ex = assertThrows(IllegalStateException.class,
                () -> webshopService.addToCart(1, 1));
        assertEquals("There are not enough products in stock, only 15 pieces", ex.getMessage());
    }

    @Test
    void removeFromCartTest() {
        webshopService.logIn("test@test.com", "dsfdfaDASF.88");

        webshopService.addToCart(1, 5);
        assertEquals(5, webshopService.getCart().get(1L));

        webshopService.removeFromCart(1);
        assertTrue(webshopService.getCart().isEmpty());
    }

    @Test
    void saveCartToOrdersTest() {
        webshopService.logIn("test@test.com", "dsfdfaDASF.88");

        webshopService.addToCart(1, 5);
        webshopService.addToCart(1, 3);
        webshopService.addToCart(2, 5);

        webshopService.saveCartToOrders(1, webshopService.getCart());
        assertEquals(0, webshopService.getCart().size());

        List<Order> orders = webshopService.listAllOrdersByUserId(webshopService.getCurrentUserId());

        assertEquals(2, orders.size());
        assertEquals(1, orders.get(0).getId());
        assertEquals(2, orders.get(1).getProductId());
        assertEquals(8, orders.get(0).getAmount());
        assertTrue(orders.get(0).getOrderTime().isAfter(LocalDateTime.now().minusMinutes(1))
                && orders.get(0).getOrderTime().isBefore(LocalDateTime.now().plusMinutes(1)));

        assertEquals(7, productDao.getStockByProductId(1));
        assertEquals(0, productDao.getStockByProductId(2));
    }

}