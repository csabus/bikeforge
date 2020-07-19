package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.factory.BikeFactory;
import hu.idevelopment.bikeforge.order.OrderList;
import hu.idevelopment.bikeforge.order.OrderListWriter;
import hu.idevelopment.bikeforge.workflow.Workflow;
import hu.idevelopment.bikeforge.workflow.WorkflowWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;

@SpringBootApplication
public class BikeforgeApplication implements CommandLineRunner {
    private final BikeFactory factory;
    private final OrderList orderList;
    private final Workflow workflow;

    @Autowired
    public BikeforgeApplication(BikeFactory factory, OrderList orderList, Workflow workflow) {
        this.factory = factory;
        this.orderList = orderList;
        this.workflow = workflow;
    }

    public static void main(String[] args) {
        SpringApplication.run(BikeforgeApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        long start = System.nanoTime();

        if (args.length == 1) {
            try {
                String orderInputFile = args[0];
                orderList.loadOrdersFromFile(orderInputFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Missing input file");
        }
        factory.findOptimalSequence();
        factory.startProductionSimulation();

        System.out.println("Profit: " + new DecimalFormat("#,###").format(orderList.calculateProfit()) + " Ft");

        OrderListWriter.writeResult(orderList, "megrendelesek.csv");
        WorkflowWriter.writeResult(workflow, "munkarend.csv");

        System.out.println("Time elapsed: " + ((double) (System.nanoTime() - start) / (double) (1000000000)) + " s");
    }
}
