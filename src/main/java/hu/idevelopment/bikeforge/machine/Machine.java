package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.order.ProductType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Machine {
    private final Map<ProductType, Integer> workingTimes = new HashMap<>();
    private final String name;
    private LocalDateTime workingStartTime;
    private LocalDateTime workingEndTime;
    private String workingOrderId;
    private int workingAmount;

    public Machine(String name) {
        this.name = name;
    }

    public void addWorkingTime(ProductType productType, int workingTime) {
        workingTimes.put(productType, workingTime);
    }

    public double getSpeed(ProductType productType) {
        return 1 / (double) workingTimes.get(productType);
    }

    public String getName() {
        return name;
    }

    public void resetWorking() {
        workingStartTime = null;
        workingEndTime = null;
        workingOrderId = null;
        workingAmount = 0;
    }

    public void increaseWorkingAmount() {
        workingAmount++;
    }

    public LocalDateTime getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(LocalDateTime workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public LocalDateTime getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(LocalDateTime workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getWorkingOrderId() {
        return workingOrderId;
    }

    public void setWorkingOrderId(String workingOrderId) {
        this.workingOrderId = workingOrderId;
    }

    public int getWorkingAmount() {
        return workingAmount;
    }

    public int getWorkingTime(ProductType productType) {
        return workingTimes.get(productType);
    }

    @Override
    public String toString() {
        return "Machine{" +
                "workingTimes=" + workingTimes +
                ", name='" + name + '\'' +
                ", workingStartTime=" + workingStartTime +
                ", workingEndTime=" + workingEndTime +
                ", workingOrderId='" + workingOrderId + '\'' +
                ", workingAmount=" + workingAmount +
                "}\n";
    }
}
