package hu.idevelopment.bikeforge.order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class OrderListWriter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd. HH:mm");
    private static final DecimalFormat numberFormatter = new DecimalFormat("#,###");

    private OrderListWriter() {
    }

    public static void writeResult(OrderList orderList, String fileName) {
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
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
