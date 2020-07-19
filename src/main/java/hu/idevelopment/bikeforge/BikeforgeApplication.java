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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;

@SpringBootApplication
public class BikeforgeApplication implements CommandLineRunner {
    private static final String IN_OUT_FOLDER = "in-out/";
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

        if (args.length == 3) {
            try {
                String orderInputFile = IN_OUT_FOLDER + args[0];
                orderList.loadOrdersFromFile(orderInputFile);

                factory.findOptimalSequence();

                factory.startProductionSimulation();

                System.out.println("Profit: " + new DecimalFormat("#,###").format(orderList.calculateProfit()) + " Ft");

                OrderListWriter.writeResult(orderList, IN_OUT_FOLDER + args[1]);
                WorkflowWriter.writeResult(workflow, IN_OUT_FOLDER + args[2]);

                System.out.println("Time elapsed: " + ((double) (System.nanoTime() - start) / (double) (1000000000)) + " s");

            } catch (IOException ex) {
                System.out.println("Input file not found: " + ex.getMessage());
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Missing input parameter");
            System.out.println("[input.csv] [megrendelesek.csv] [munkarend.csv]");
        }
    }
}
