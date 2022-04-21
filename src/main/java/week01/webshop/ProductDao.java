package week01.webshop;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ProductDao {

    JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
