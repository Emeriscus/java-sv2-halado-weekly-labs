package week01.webshop;

import week01.webshop.utils.PasswordGenerator;
import week01.webshop.utils.UserValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    public void logIn(String email, String password) {
        String salt = userDao.getSaltByEmail(email);
        String securePassword = userDao.getSecurePasswordByEmail(email);
        if (PasswordGenerator.verifyUserPassword(password, securePassword, salt)) {
            loggedIn = true;
            currentUserId = userDao.getUserIdByEmail(email);
        } else {
            loggedIn = false;
        }
    }

    public void addToCart(long productId, long quantity) {
        validateLoggedIn();
        long totalQuantity;
        if (cart.containsKey(productId)) {
            totalQuantity = cart.get(productId) + quantity;
        } else {
            totalQuantity = quantity;
        }
        validateStock(productId, totalQuantity);
        if (cart.containsKey(productId)) {
            cart.put(productId, cart.get(productId) + quantity);
        } else {
            cart.put(productId, quantity);
        }
    }

    public void removeFromCart(long productId) {
        validateLoggedIn();
        cart.remove(productId);
    }

    public void saveCartToOrders(long currentUserId, Map<Long, Long> cart) {
        validateLoggedIn();
        List<Order> orders = convertCartToOrders(cart);
        cart.forEach(this::validateStock);
        cart.forEach((k, v) -> productDao.updateStockById(k, -v));
        orderDao.addOrders(currentUserId, orders);
        cart.clear();
    }

    private List<Order> convertCartToOrders(Map<Long, Long> cart) {
        List<Order> result = new ArrayList<>();
        for (Map.Entry<Long, Long> actual : cart.entrySet()) {
            result.add(new Order(actual.getKey(), actual.getValue(), LocalDateTime.now()));
        }
        return result;
    }

    public List<Order> listAllOrdersByUserId(long userId) {
        validateLoggedIn();
        return orderDao.listOrdersByUserId(userId);
    }

    private void validateLoggedIn() {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
    }

    private void validateStock(long productId, long amount) {
        long stock = productDao.getStockByProductId(productId);
        if (stock < amount) {
            throw new IllegalStateException("There are not enough products in stock, only " + stock + " pieces");
        }
    }

}
