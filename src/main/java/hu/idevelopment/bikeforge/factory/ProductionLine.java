package hu.idevelopment.bikeforge.factory;

import hu.idevelopment.bikeforge.helper.DateTimeHelper;
import hu.idevelopment.bikeforge.machine.Machine;
import hu.idevelopment.bikeforge.machine.MachineConfigReader;
import hu.idevelopment.bikeforge.machine.MachineGroup;
import hu.idevelopment.bikeforge.order.Order;
import hu.idevelopment.bikeforge.order.OrderList;
import hu.idevelopment.bikeforge.order.ProductType;
import hu.idevelopment.bikeforge.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductionLine {
    private final List<MachineGroup> productionLineItems = new ArrayList<>();
    private final MachineConfigReader machineConfigReader;
    private final Workflow workflow;

    @Autowired
    public ProductionLine(MachineConfigReader machineConfigReader, Workflow workflow) {
        this.machineConfigReader = machineConfigReader;
        this.workflow = workflow;
        buildProductionLine();
    }

    private void buildProductionLine() {
        for (MachineConfigReader.MachineConfig machineConfig : machineConfigReader.getMachineGroups()) {
            MachineGroup machineGroup = new MachineGroup(workflow);
            for (String name : machineConfig.getNames()) {
                Machine machine = new Machine(name);
                setWorkingTimes(machine, machineConfig.getTimes());
                machineGroup.addMachine(machine);
            }
            productionLineItems.add(machineGroup);
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

    public int getProductionTimeForOrder(Order order) {
        int slowestMachineIndex = getSlowestMachineIndex(order);
        ProductType productType = order.getProductType();
        int productionTime = cumulatedProductionTime(productType, 0, slowestMachineIndex);
        productionTime += cumulatedProductionTime(productType, slowestMachineIndex, slowestMachineIndex + 1) * order.getQuantity();
        productionTime += cumulatedProductionTime(productType, slowestMachineIndex + 1, productionLineItems.size());
        return productionTime;
    }

    private int getSlowestMachineIndex(Order order) {
        int index = 0;
        int slowestMachineIndex = 0;
        double minimumSpeed = Double.MAX_VALUE;
        for (MachineGroup machineGroup : productionLineItems) {
            double cumulatedSpeed = machineGroup.getMachine(0).getSpeed(order.getProductType()) * machineGroup.getMachineCount();
            if (cumulatedSpeed < minimumSpeed) {
                minimumSpeed = cumulatedSpeed;
                slowestMachineIndex = index;
            }
            index++;
        }
        return slowestMachineIndex;
    }

    private int cumulatedProductionTime(ProductType productType, int from, int to) {
        int productionTime = 0;
        for (int i = from; i < to; i++) {
            Machine machine = productionLineItems.get(i).getMachine(0);
            int machineCount = productionLineItems.get(i).getMachineCount();
            double cumulatedSpeed = machine.getSpeed(productType) * machineCount;
            productionTime += 1 / cumulatedSpeed;
        }
        return productionTime;
    }

    public void startProductionSimulation(OrderList orderList) {
        for (int i = 0; i < orderList.getOrderCount(); i++) {
            Order order = orderList.getOrder(i);
            LocalDateTime startTime = DateTimeHelper.DEFAULT_START_DATE;
            if (i > 0) {
                startTime = orderList.getOrder(i - 1).getLastFinishedDate();
            }
            order.setActualStartTime(startTime);
            for (MachineGroup machineGroup : productionLineItems) {
                machineGroup.setInputBuffer(order.getOrderItems());
                machineGroup.startProduction();
            }
            order.setActualDeadlineByLastItemFinishDate();
        }
    }
}
