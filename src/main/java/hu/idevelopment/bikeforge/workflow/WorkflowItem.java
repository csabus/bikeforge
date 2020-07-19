package hu.idevelopment.bikeforge.workflow;

import java.time.LocalDateTime;

public class WorkflowItem {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String machineName;
    private String orderId;
    private int quantity;

    public WorkflowItem(LocalDateTime startTime, LocalDateTime endTime, String machineName, String orderId, int quantity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.machineName = machineName;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getMachineName() {
        return machineName;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "WorkflowItem{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", machineName=" + machineName +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                "}\n";
    }
}
