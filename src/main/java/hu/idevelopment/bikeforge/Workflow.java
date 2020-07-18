package hu.idevelopment.bikeforge;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Workflow {
    private List<WorkflowItem> items = new ArrayList<>();

    public void addItem(WorkflowItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "items=" + items +
                '}';
    }
}
