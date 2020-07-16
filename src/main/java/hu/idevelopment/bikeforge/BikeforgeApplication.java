package hu.idevelopment.bikeforge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BikeforgeApplication implements CommandLineRunner {
    private Factory factory;

    @Autowired
    public BikeforgeApplication(Factory factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(BikeforgeApplication.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 1) {
            try {
                readInput(args[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Missing input file");
        }

        //System.out.println(factory.getProductionTime(ProductType.SB));
    }

    private void readInput(String inputFileName) {
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

    }
}
