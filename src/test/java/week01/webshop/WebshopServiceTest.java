package week01.webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.nio.file.Path;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WebshopServiceTest {

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

        UserDao userDao = new UserDao(dataSource);
        OrderDao orderDao = new OrderDao(dataSource);
        ProductDao productDao = new ProductDao(dataSource);

        WebshopService webshopService = new WebshopService(userDao, orderDao, productDao);
//        webshopService.loadProductsFromFile(Path.of("src/test/resources/products.csv"));
        webshopService.saveUser("test@test.com", "dsfdfaDASF.88");
        webshopService.saveUser("othertest@othertest.com", "zujujFGTZJH.656");
//        webshopService.logIn("jack_doe", "1234");
    }

    @Test
    void saveUserTest() {


    }
}