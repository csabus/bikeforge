package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.helper.DateTimeHelper;
import hu.idevelopment.bikeforge.order.OrderItem;
import hu.idevelopment.bikeforge.workflow.Workflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MachineGroup {
    List<Integer> processingTimes = new ArrayList<>();
    private final List<Machine> machineList = new ArrayList<>();
    private List<OrderItem> inputBuffer = new ArrayList<>();
    private Workflow workflow;

    public MachineGroup(Workflow workflow) {
        this.workflow = workflow;
    }

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

    public void setInputBuffer(List<OrderItem> inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public void startProduction() {
        machineList.forEach(Machine::resetWorking);
        for (int i = 0; i < inputBuffer.size(); i++) {
            int machineIndex = i % machineList.size();
            Machine machine = machineList.get(machineIndex);
            machine.increaseWorkingAmount();
            OrderItem orderItem = inputBuffer.get(i);

            if (i < machineList.size()) {
                LocalDateTime startTime = DateTimeHelper.addMinutesToDate(DateTimeHelper.DEFAULT_START_DATE, Math.max(processingTimes.get(machineIndex), orderItem.getProcessingTime()));
                machine.setWorkingStartTime(startTime);
                machine.setWorkingOrderId(orderItem.getOrderId());
            }

            int orderItemProcessingTime = machine.getWorkingTime(orderItem.getProductType());
            processingTimes.set(machineIndex, Math.max(processingTimes.get(machineIndex), orderItem.getProcessingTime()) + orderItemProcessingTime);
            orderItem.setProcessingTime(processingTimes.get(machineIndex));

            if (i >= inputBuffer.size() - machineList.size()) {
                machine.setWorkingEndTime(orderItem.getFinishDate());
                machine.setWorkingDuration(orderItem.getProcessingTime());
            }
        }

        for (Machine machine : machineList) {
            /*WorkflowItem workflowItem = new WorkflowItem(
                    machine.getWorkingStartTime(),
                    machine.getWorkingEndTime(),
                    machine.getName(),
                    machine.getWorkingOrderId(),
                    machine.getWorkingAmount());
            workflow.addItem(workflowItem);*/
            workflow.createWorkflowItems(
                    machine.getWorkingStartTime(),
                    machine.getWorkingEndTime(),
                    machine.getName(),
                    machine.getWorkingOrderId(),
                    machine.getWorkingAmount());
        }
    }

}
