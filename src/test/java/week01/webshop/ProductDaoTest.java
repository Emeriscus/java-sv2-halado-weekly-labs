package week01.webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    Flyway flyway;
    ProductDao productDao;

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

        productDao = new ProductDao(dataSource);
        productDao.addProduct(new Product("Milk", 250, 15));
        productDao.addProduct(new Product("Wine", 700, 5));
        productDao.addProduct(new Product("Butter", 200, 12));
        productDao.addProduct(new Product("Bread", 350, 17));
    }

    @Test
    void getAllProductsTest() {
        List<Product> products = productDao.getAllProducts();

        assertEquals(4, products.size());
        assertEquals("Wine", products.get(1).getName());
    }
}