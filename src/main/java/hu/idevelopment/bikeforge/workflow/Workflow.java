package hu.idevelopment.bikeforge.workflow;

import hu.idevelopment.bikeforge.helper.DateTimeHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class Workflow {
    private List<WorkflowItem> items = new ArrayList<>();

    public void addItem(WorkflowItem item) {
        items.add(item);
    }

    public List<WorkflowItem> getItems() {
        return items;
    }

    public void createWorkflowItems(LocalDateTime startTime, LocalDateTime endTime, String machineName, String orderId, int quantity) {
        LocalDateTime start = startTime.plusMinutes(0);
        long totalDuration = ChronoUnit.MINUTES.between(startTime, endTime);
        long remainMinutes = totalDuration;
        while (remainMinutes > 0) {
            LocalDateTime startDayEnd = LocalDateTime.of(start.toLocalDate(), DateTimeHelper.WORK_END);
            long dayMinutes = ChronoUnit.MINUTES.between(start, startDayEnd);
            if (dayMinutes >= remainMinutes) {
                int actualQuantity = (int) ((quantity * remainMinutes) / totalDuration);
                WorkflowItem item = new WorkflowItem(start, start.plusMinutes(remainMinutes), machineName, orderId, actualQuantity);
                items.add(item);
                break;
            }
            int actualQuantity = (int) ((quantity * dayMinutes) / totalDuration * (1440 / DateTimeHelper.NOT_WORKING_MINUTES));
            WorkflowItem item = new WorkflowItem(start, startDayEnd, machineName, orderId, actualQuantity);
            items.add(item);
            start = LocalDateTime.of(start.toLocalDate().plusDays(1), DateTimeHelper.WORK_START);
            remainMinutes -= dayMinutes;
            remainMinutes -= DateTimeHelper.NOT_WORKING_MINUTES;
        }
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "items=" + items +
                '}';
    }
}
