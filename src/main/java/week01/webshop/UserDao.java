package week01.webshop;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveUser(String email, String salt, String securePassword) {
        //language=sql
        try {
            jdbcTemplate.update("insert into users(email, salt, secure_password) values (?,?,?)", email, salt, securePassword);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("The email is already taken: " + email, e);
        }
    }

    public long getUserIdByEmail(String email) {
        //language=sql
        try {
            return jdbcTemplate.queryForObject("select * from users where email=?",
                    (rs, rowNum) -> rs.getLong("user_id"), email);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new IllegalStateException("Cannot find email: " + email, e);
        }
    }

    public String getSaltByEmail(String email) {
        //language=sql
        try {
            return jdbcTemplate.queryForObject("select * from users where email=?",
                    (rs, rowNum) -> rs.getString("salt"), email);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new IllegalStateException("Cannot find email: " + email, e);
        }
    }

    public String getSecurePasswordByEmail(String email) {
        //language=sql
        try {
            return jdbcTemplate.queryForObject("select * from users where email=?",
                    (rs, rowNum) -> rs.getString("secure_password"), email);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new IllegalStateException("Cannot find email: " + email, e);
        }
    }
}
