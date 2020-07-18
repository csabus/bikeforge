package hu.idevelopment.bikeforge.workflow;

import org.springframework.stereotype.Component;

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

    @Override
    public String toString() {
        return "Workflow{" +
                "items=" + items +
                '}';
    }
}
