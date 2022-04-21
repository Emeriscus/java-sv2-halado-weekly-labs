package week01.webshop;

import week01.webshop.utils.PasswordGenerator;
import week01.webshop.utils.UserValidator;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebshopService {

    private boolean loggedIn;
    private long currentUserId;
    private Map<Long, Long> cart = new LinkedHashMap<>();

    private UserDao userDao;
    private OrderDao orderDao;
    private ProductDao productDao;

    public WebshopService(UserDao userDao, OrderDao orderDao, ProductDao productDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public long getCurrentUserId() {
        return currentUserId;
    }

    public Map<Long, Long> getCart() {
        return cart;
    }

    public void saveUser(String email, String password) {
        if (UserValidator.validateUser(email, password)) {
            String salt = PasswordGenerator.generateSalt();
            String securePassword = PasswordGenerator.generateSecurePassword(password, salt);
            userDao.saveUser(email, salt, securePassword);
        }
    }
}
