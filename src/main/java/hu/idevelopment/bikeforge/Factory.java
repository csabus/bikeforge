package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.machine.Machine;
import hu.idevelopment.bikeforge.machine.MachineFactory;
import hu.idevelopment.bikeforge.machine.MachineType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Factory {
    //private final ProductionLine productionLine = new ProductionLine();
    private final List<List<Machine>> particularMachines = new ArrayList<>();

    public Factory() {
        addMachines(MachineType.CUT, 6);
        addMachines(MachineType.CURVE, 2);
        addMachines(MachineType.WELD, 3);
        addMachines(MachineType.TEST, 1);
        addMachines(MachineType.PAINT, 4);
        addMachines(MachineType.PACK, 3);
    }

    private void addMachines(MachineType type, int count) {
        List<Machine> machineList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Machine machine = MachineFactory.createMachine(type, i);
            if (machine != null) {
                machineList.add(machine);
            }
        }
        particularMachines.add(machineList);
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
        int slowestMachineIndex = getSlowestMachineIndex(order);
        int productionTime = sumProductionTime(order.getProductType(), 0, slowestMachineIndex);
        productionTime += sumProductionTime(order.getProductType(), slowestMachineIndex, slowestMachineIndex + 1) * order.getQuantity();
        productionTime += sumProductionTime(order.getProductType(), slowestMachineIndex + 1, particularMachines.size());
        return productionTime;
    }

    private int getSlowestMachineIndex(Order order) {
        int index = 0;
        int slowestMachineIndex = 0;
        double minimumSpeed = Double.MAX_VALUE;
        for (List<Machine> machineList : particularMachines) {
            double cumulatedSpeed = machineList.get(0).getSpeed(order.getProductType()) * machineList.size();
            if (cumulatedSpeed < minimumSpeed) {
                minimumSpeed = cumulatedSpeed;
                slowestMachineIndex = index;
            }
            index++;
        }
        return slowestMachineIndex;
    }

    private int sumProductionTime(ProductType productType, int from, int to) {
        int productionTime = 0;
        for (int i = from; i < to; i++) {
            Machine machine = particularMachines.get(i).get(0);
            int machineCount = particularMachines.get(i).size();
            double cumulatedSpeed = machine.getSpeed(productType) * machineCount;
            productionTime += 1 / cumulatedSpeed;
        }
        return productionTime;
    }

}
