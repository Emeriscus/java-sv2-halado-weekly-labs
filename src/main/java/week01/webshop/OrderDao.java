package week01.webshop;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    public OrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOrders(long userId, List<Order> orders) {
        //language=sql
        for (Order actual : orders) {
            jdbcTemplate.update("insert into orders(user_id, product_id, product_amount, order_date) values (?,?,?,?)",
                    userId, actual.getProductId(), actual.getAmount(), actual.getOrderTime());
        }
    }

    public List<Order> listAllOrders() {
        //language=sql
        return jdbcTemplate.query(
                "select * from orders", (rs, rownum) -> new Order(
                        rs.getLong("product_id"), rs.getLong("product_amount"),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
    }

    public List<Order> listOrdersByUserId(long userId) {
        //language=sql
        return jdbcTemplate.query("select * from orders where user_id=?", (rs, rowNum) -> new Order(
                rs.getLong("product_id"), rs.getLong("product_amount"),
                rs.getTimestamp("order_date").toLocalDateTime()
        ), userId);
    }
}
