package hu.idevelopment.bikeforge.order;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class OrderList {
    private List<Order> orders = new ArrayList<>();

    public void loadOrdersFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            Order order = Order.newOrder(line);
            if (order != null) {
                orders.add(order);
            }
        }
        orders.sort(Comparator.comparing(Order::getDeadline));
        //orders.sort(Comparator.comparingInt(Order::getQuantity));
        //orders.sort((o1, o2) -> Double.compare(o2.getPenalty(), o1.getPenalty()));

    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void replaceItems(int from, int to) {
        Order order = orders.get(from);
        orders.set(from, orders.get(to));
        orders.set(to, order);
    }

    public double calculateProfit() {
        double profit = 0;
        for (Order order : orders) {
            profit += order.getActualProfit();
        }
        return profit;
    }

    public int getOrderCount() {
        return orders.size();
    }

    public Order getOrder(int index) {
        return orders.get(index);
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "orders=" + orders +
                '}';
    }
}
