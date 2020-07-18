package hu.idevelopment.bikeforge.output;

import hu.idevelopment.bikeforge.order.Order;
import hu.idevelopment.bikeforge.order.OrderList;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Component
public class Summary {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd. HH:mm");
    private final DecimalFormat numberFormatter = new DecimalFormat("#,###");

    public void printResult(OrderList orderList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Megrendelésszám;Profit összesen;Levont kötbér;Munka megkezdése;Készrejelentés ideje;Megrendelés eredeti határideje\n");
        for (Order order : orderList.getOrders()) {
            sb.append(order.getId()).append(";");
            sb.append(numberFormatter.format(order.getActualProfit())).append(" Ft;");
            sb.append(numberFormatter.format(order.getActualPenalty())).append(" Ft;");
            sb.append(order.getActualStartTime().format(dateFormatter)).append(";");
            sb.append(order.getActualDeadline().format(dateFormatter)).append(";");
            sb.append(order.getDeadline().format(dateFormatter)).append(";\n");
        }
        System.out.println(sb.toString());
    }
}
