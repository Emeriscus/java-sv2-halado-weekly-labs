package week01.webshop;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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

    public List<Product> getAllProducts() {
        //language=sql
        return jdbcTemplate.query("select * from products",
                (rs, rowNum) -> new Product(rs.getLong("product_id"), rs.getString("product_name"),
                        rs.getInt("price"), rs.getInt("stock")));
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

    public void updateStockById(long productId, long quantity) {
        //language=sql
        jdbcTemplate.update("update products set stock=stock+? where product_id=?", quantity, productId);
    }
}
