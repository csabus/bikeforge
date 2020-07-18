package hu.idevelopment.bikeforge.factory;

import hu.idevelopment.bikeforge.helper.DateTimeHelper;
import hu.idevelopment.bikeforge.order.Order;
import hu.idevelopment.bikeforge.order.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BikeFactory {
    private final ProductionLine productionLine;
    private final OrderList orderList;

    @Autowired
    public BikeFactory(ProductionLine productionLine, OrderList orderList) {
        this.productionLine = productionLine;
        this.orderList = orderList;
    }

    public int getProductionTimeForOrder(Order order) {
        return productionLine.getProductionTimeForOrder(order);
    }

    public void findOptimalSequence() {
        calculateActualDeadlines();
        List<Order> optimalOrderList = orderList.getOrders();
        double maxProfit = orderList.calculateProfit();
        boolean isOptimal = false;
        while (!isOptimal) {
            int numberOfChanges = 0;
            for (int i = 0; i < orderList.getOrders().size() - 1; i++) {
                orderList.replaceItems(i, i + 1);
                calculateActualDeadlines();
                double actualProfit = orderList.calculateProfit();
                if (actualProfit > maxProfit) {
                    maxProfit = actualProfit;
                    optimalOrderList = List.copyOf(orderList.getOrders());
                    numberOfChanges++;
                } else {
                    orderList.replaceItems(i + 1, i);
                }
            }
            if (numberOfChanges == 0) {
                isOptimal = true;
            }
        }
        orderList.setOrders(optimalOrderList);
        //System.out.println(orderList);
        System.out.printf("%f%n", maxProfit);
    }

    public void buildWorkflow() {
        //productionLine.buildWorkflow(orderList);
    }

    private void calculateActualDeadlines() {
        LocalDateTime startDate = DateTimeHelper.DEFAULT_START_DATE;
        for (Order order : orderList.getOrders()) {
            int productionTime = getProductionTimeForOrder(order);
            startDate = DateTimeHelper.addMinutesToDate(startDate, productionTime);
            order.setActualDeadline(startDate);
        }
    }

    public void startProductionSimulation() {
        productionLine.startProductionSimulation(orderList);
    }
}
