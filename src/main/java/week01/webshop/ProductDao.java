package week01.webshop;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ProductDao {

    JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProduct(Product product) {
        //language=sql
        jdbcTemplate.update("insert into products(product_name, price, stock) values(?,?,?)",
                product.getName(), product.getPrice(), product.getStock());
    }

    public long getStockByProductId(long productId) {
        //language=sql
        try {
            return jdbcTemplate.queryForObject("select * from products where product_id=?",
                    (rs, rowNum) -> rs.getLong("stock"), productId);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new IllegalStateException("Cannot find product!", e);
        }
    }

    public void updateStockById(Long productId, long quantity) {
        //language=sql
        jdbcTemplate.update("update products set stock=stock+? where product_id=?", quantity, productId);
    }
}
