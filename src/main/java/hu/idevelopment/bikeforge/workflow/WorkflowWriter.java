package hu.idevelopment.bikeforge.workflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class WorkflowWriter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private WorkflowWriter() {
    }

    public static void writeResult(Workflow workflow, String fileName) {
        workflow.getItems().sort((Comparator.comparing(WorkflowItem::getStartTime)));
        StringBuilder sb = new StringBuilder();
        sb.append("Dátum;Gép;Kezdő időpont;Kezdő időpont;Megrendelésszám;Darabszám\n");
        for (WorkflowItem item : workflow.getItems()) {
            sb.append(dateFormatter.format(item.getStartTime())).append(";");
            sb.append(item.getMachineName()).append(";");
            sb.append(timeFormatter.format(item.getStartTime())).append(";");
            sb.append(timeFormatter.format(item.getEndTime())).append(";");
            sb.append(item.getOrderId()).append(";");
            sb.append(item.getQuantity()).append(";\n");
        }
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
