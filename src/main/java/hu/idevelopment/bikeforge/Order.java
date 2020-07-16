package hu.idevelopment.bikeforge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class Order {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
    private String id;
    private ProductType productType;
    private int quantity;
    private LocalDateTime deadline;
    private long profit;
    private long penalty;

    private Order() {
    }

    private Order(String id, ProductType productType, int quantity, LocalDateTime deadline, long profit, long penalty) {
        this.id = id;
        this.productType = productType;
        this.quantity = quantity;
        this.deadline = deadline;
        this.profit = profit;
        this.penalty = penalty;
    }

    public static Order newOrder(String inputLine) {
        Order order = null;
        int year = LocalDateTime.now().getYear();
        try {
            String[] items = inputLine.split(";");
            if (items.length == 6) {
                String id = items[0];
                ProductType productType = ProductType.valueOf(items[1]);
                int quantity = Integer.parseInt(items[2]);
                LocalDateTime deadline = LocalDateTime.parse(year + "." + items[3], formatter);
                long profit = Long.parseLong(items[4]);
                long penalty = Long.parseLong(items[5].replace(" ", ""));
                order = new Order(id, productType, quantity, deadline, profit, penalty);
            }
        } catch (Exception ex) {
            throw new InputMismatchException("Wrong input");
        }
        return order;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", productType=" + productType +
                ", quantity=" + quantity +
                ", deadline=" + deadline +
                ", profit=" + profit +
                ", penalty=" + penalty +
                '}';
    }
}
