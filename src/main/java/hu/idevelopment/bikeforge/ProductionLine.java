package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.machine.Config;
import hu.idevelopment.bikeforge.machine.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductionLine {
    private final List<List<Machine>> productionLineItems = new ArrayList<>();
    private final Config config;

    @Autowired
    public ProductionLine(Config config) {
        this.config = config;
        buildProductionLine();
    }

    private void buildProductionLine() {
        for (Config.MachineConfig machineGroup : config.getMachineGroups()) {
            List<Machine> machineGroupList = new ArrayList<>();
            for (String name : machineGroup.getNames()) {
                Machine machine = new Machine(name);
                setWorkingTimes(machine, machineGroup.getTimes());
                machineGroupList.add(machine);
            }
            productionLineItems.add(machineGroupList);
        }
    }

    private void setWorkingTimes(Machine machine, List<Map<String, Integer>> times) {
        for (Map<String, Integer> timeItems : times) {
            for (Map.Entry<String, Integer> entry : timeItems.entrySet()) {
                ProductType productType = ProductType.valueOf(entry.getKey());
                int workingTime = entry.getValue();
                machine.addWorkingTime(productType, workingTime);
            }
        }
    }

    /*private void addMachines(MachineType type, int count) {
        List<MachineInterface> machineInterfaceList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            MachineInterface machineInterface = MachineFactory.createMachine(type, i);
            if (machineInterface != null) {
                machineInterfaceList.add(machineInterface);
            }
        }
        productionLineItems.add(machineInterfaceList);
    }*/

    public int getProductionTimeForOrder(Order order) {
        int slowestMachineIndex = getSlowestMachineIndex(order);
        int productionTime = sumProductionTime(order.getProductType(), 0, slowestMachineIndex);
        productionTime += sumProductionTime(order.getProductType(), slowestMachineIndex, slowestMachineIndex + 1) * order.getQuantity();
        productionTime += sumProductionTime(order.getProductType(), slowestMachineIndex + 1, productionLineItems.size());
        return productionTime;
    }

    private int getSlowestMachineIndex(Order order) {
        int index = 0;
        int slowestMachineIndex = 0;
        double minimumSpeed = Double.MAX_VALUE;
        for (List<Machine> machineList : productionLineItems) {
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
            Machine machine = productionLineItems.get(i).get(0);
            int machineCount = productionLineItems.get(i).size();
            double cumulatedSpeed = machine.getSpeed(productType) * machineCount;
            productionTime += 1 / cumulatedSpeed;
        }
        return productionTime;
    }

}
