package hu.idevelopment.bikeforge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Factory {
    private final ProductionLine productionLine;
    private final OrderList orderList;

    @Autowired
    public Factory(ProductionLine productionLine, OrderList orderList) {
        this.productionLine = productionLine;
        this.orderList = orderList;
    }

    /*public int getProductionTime(ProductType productType) {
        int productionTime = 0;
        for (List<Machine> machineList : particularMachines) {
            double speed = machineList.get(0).getSpeed(productType);
            productionTime += 1 / speed;
        }
        return productionTime;
    }*/

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
                    optimalOrderList = orderList.getOrders();
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
        System.out.println(maxProfit);
    }

    private void calculateActualDeadlines() {
        LocalDateTime startDate = LocalDateTime.of(2020, 7, 20, 6, 0, 0);
        for (Order order : orderList.getOrders()) {
            int productionTime = getProductionTimeForOrder(order);
            startDate = calculateEndDateTime(startDate, productionTime);
            order.setActualDeadline(startDate);
        }
    }

    private LocalDateTime calculateEndDateTime(LocalDateTime startDate, long minutes) {
        LocalDateTime dayStart = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 6, 0, 0);
        LocalDateTime startDayEnd = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 22, 0, 0);
        long dayRemainMinutes = ChronoUnit.MINUTES.between(startDate, startDayEnd);
        if (minutes <= dayRemainMinutes) {
            return startDate.plusMinutes(minutes);
        }

        long remainMinutes = minutes - dayRemainMinutes;
        while (remainMinutes > 0) {
            dayStart = dayStart.plusDays(1);
            startDayEnd = LocalDateTime.of(dayStart.getYear(), dayStart.getMonth(), dayStart.getDayOfMonth(), 22, 0, 0);
            dayRemainMinutes = ChronoUnit.MINUTES.between(dayStart, startDayEnd);
            remainMinutes -= dayRemainMinutes;
        }

        return dayStart.plusMinutes(960 + remainMinutes);
    }


}
