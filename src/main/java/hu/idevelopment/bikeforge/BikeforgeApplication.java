package hu.idevelopment.bikeforge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BikeforgeApplication implements CommandLineRunner {
    private final Factory factory;
    private final OrderList orderList;

    @Autowired
    public BikeforgeApplication(Factory factory, OrderList orderList) {
        this.factory = factory;
        this.orderList = orderList;
    }

    public static void main(String[] args) {
        SpringApplication.run(BikeforgeApplication.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
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
    }

    /*private void readInput(String inputFileName) {
        Path path = Paths.get(inputFileName);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            Order order = Order.newOrder(line);
            System.out.println(factory.getProductionTimeForOrder(order));
        }

    }*/
}
