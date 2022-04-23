package week01.webshop;

import java.time.LocalDateTime;

public class Order {

    private long id;
    private long productId;
    private long amount;
    private LocalDateTime orderTime;

    public Order(long id, long productId, long amount, LocalDateTime orderTime) {
        this.id = id;
        this.productId = productId;
        this.amount = amount;
        this.orderTime = orderTime;
    }

    public Order(long productId, long amount, LocalDateTime orderTime) {
        this.productId = productId;
        this.amount = amount;
        this.orderTime = orderTime;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", amount=" + amount +
                ", orderTime=" + orderTime +
                '}';
    }
}
