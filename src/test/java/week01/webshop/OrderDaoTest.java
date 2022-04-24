package week01.webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

    Flyway flyway;
    OrderDao orderDao;

    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/webshop_emeriscus_test?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot reach DataBase!", e);
        }
        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        orderDao = new OrderDao(dataSource);

        orderDao.addOrders(1, List.of(new Order(1, 3, LocalDateTime.now()),
                new Order(2, 4, LocalDateTime.now()),
                new Order(3, 2, LocalDateTime.now())));
        orderDao.addOrders(2, List.of(new Order(1, 2, LocalDateTime.now())));
    }

    @Test
    void addOrdersTest() {
        orderDao.addOrders(3, List.of(new Order(1, 5, LocalDateTime.now())));

        List<Order> results = orderDao.getAllOrders();
        assertEquals(5, results.size());
        assertEquals(2, results.get(3).getAmount());
    }

    @Test
    void getAllOrders() {
        orderDao.addOrders(4, List.of(new Order(10, 150, LocalDateTime.now())));
        List<Order> result = orderDao.getAllOrders();
        assertEquals(5, result.size());
    }

    @Test
    void getOrdersByUserIdTest() {
        List<Order> result = orderDao.getOrdersByUserId(1);
        assertEquals(3, result.size());

        List<Order> newResult = orderDao.getOrdersByUserId(2);
        assertEquals(1, newResult.size());
    }
}