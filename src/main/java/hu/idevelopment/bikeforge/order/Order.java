package hu.idevelopment.bikeforge.order;

import hu.idevelopment.bikeforge.product.ProductType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Order {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
    private String id;
    private ProductType productType;
    private int quantity;
    private LocalDateTime deadline;
    private long profit;
    private long penalty;
    private LocalDateTime actualDeadline;
    private final List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime actualStartTime;

    private Order() {
    }

    private Order(String id, ProductType productType, int quantity, LocalDateTime deadline, long profit, long penalty) {
        this.id = id;
        this.productType = productType;
        this.quantity = quantity;
        this.deadline = deadline;
        this.profit = profit;
        this.penalty = penalty;
        for (int i = 0; i < quantity; i++) {
            orderItems.add(new OrderItem(productType));
        }
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

    public void setActualDeadline(LocalDateTime actualDeadline) {
        this.actualDeadline = actualDeadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public double getActualProfit() {
        double actualPenalty = getActualPenalty();
        //System.out.println(id + ": " + (profit * quantity - actualPenalty));
        return profit * quantity - actualPenalty;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getActualPenalty() {
        if (actualDeadline.isAfter(deadline)) {
            long delayMinutes = ChronoUnit.MINUTES.between(deadline, actualDeadline);
            return Math.ceil(delayMinutes / 1440.) * penalty;
        } else {
            return 0;
        }
    }

    public String getId() {
        return id;
    }

    public void setActualDeadlineByLastItemFinishDate() {
        actualDeadline = orderItems.get(orderItems.size() - 1).getFinishDate();
    }

    public LocalDateTime getLastFinishedDate() {
        return orderItems.get(orderItems.size() - 1).getFinishDate();
    }

    public LocalDateTime getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(LocalDateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public LocalDateTime getActualDeadline() {
        return actualDeadline;
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
                ", actualDeadline=" + actualDeadline +
                ", actualStartTime=" + actualStartTime +
                "}\n";
    }
}
