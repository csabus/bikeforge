package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.factory.BikeFactory;
import hu.idevelopment.bikeforge.order.OrderList;
import hu.idevelopment.bikeforge.output.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BikeforgeApplication implements CommandLineRunner {
    private final BikeFactory factory;
    private final OrderList orderList;
    private final Summary summary;

    @Autowired
    public BikeforgeApplication(BikeFactory factory, OrderList orderList, Summary summary) {
        this.factory = factory;
        this.orderList = orderList;
        this.summary = summary;
    }

    public static void main(String[] args) {
        SpringApplication.run(BikeforgeApplication.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
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
        //factory.buildWorkflow();
        factory.startProductionSimulation();
        summary.printResult(orderList);
        System.out.println("Time elapsed: " + ((double) (System.nanoTime() - start) / (double) (1000000000)) + " s");
    }
}
