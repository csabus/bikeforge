package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.order.OrderItem;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MachineGroup {
    List<Integer> processingTimes = new ArrayList<>();
    private List<Machine> machineList = new ArrayList<>();
    private Deque<OrderItem> inputBuffer = new LinkedList<>();
    private Deque<OrderItem> outputBuffer = new LinkedList<>();

    public void addMachine(Machine machine) {
        machineList.add(machine);
        processingTimes.add(0);
    }

    public int getMachineCount() {
        return machineList.size();
    }

    public Machine getMachine(int index) {
        return machineList.get(index);
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void addOrderItemToInputBuffer(OrderItem orderItem) {
        inputBuffer.addLast(orderItem);
    }

    public void startProduction() {
        while (!inputBuffer.isEmpty()) {
            for (int i = 0; i < machineList.size(); i++) {
                if (inputBuffer.isEmpty()) {
                    break;
                }
                Machine machine = machineList.get(i);
                OrderItem orderItem = inputBuffer.removeFirst();
                int orderItemProcessingTime = machine.getWorkingTime(orderItem.getProductType());
                processingTimes.set(i, Math.max(processingTimes.get(i), orderItem.getProcessingTime()) + orderItemProcessingTime);
                orderItem.setProcessingTime(processingTimes.get(i));
                outputBuffer.addLast(orderItem);
            }
        }
    }

}
