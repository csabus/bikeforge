package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.ProductType;

import java.util.HashMap;
import java.util.Map;

public class Machine {
    private final Map<ProductType, Integer> workingTimes = new HashMap<>();
    private final String name;

    public Machine(String name) {
        this.name = name;
    }

    public void addWorkingTime(ProductType productType, int workingTime) {
        workingTimes.put(productType, workingTime);
    }

    public double getSpeed(ProductType productType) {
        return 1 / (double) workingTimes.get(productType);
    }

    @Override
    public String toString() {
        return "Machine{" +
                "workingTimes=" + workingTimes +
                ", name='" + name + '\'' +
                '}';
    }
}
