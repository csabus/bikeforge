package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.machine.Machine;
import hu.idevelopment.bikeforge.order.Order;

import java.time.LocalDateTime;

public class WorkflowItem {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Machine machine;
    private Order order;
    private int quantity;

    public WorkflowItem(LocalDateTime startTime, LocalDateTime endTime, Machine machine, Order order, int quantity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.machine = machine;
        this.order = order;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WorkflowItem{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", machine=" + machine +
                ", order=" + order +
                ", quantity=" + quantity +
                "}\n";
    }
}
