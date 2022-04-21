package week01.webshop;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveUser(String email, String salt, String securePassword) {
        //language=sql
        jdbcTemplate.update("insert into users(email, salt, secure_password) values (?,?,?)", email, salt, securePassword);
    }

    public long logIn(String email, String securePassword) {
        //language=sql
        return jdbcTemplate.queryForObject("select * from users where email=? and secure_password=?",
                (rs, rowNum) -> rs.getLong("email"), email, securePassword);
    }

    public long getSaltByUserEmail(String email) {
        return jdbcTemplate.queryForObject("select * from users where email=?",
                (rs, rowNum) -> rs.getLong("salt"), email);
    }
}
