package hu.idevelopment.bikeforge;

import hu.idevelopment.bikeforge.machine.*;

import java.util.ArrayList;
import java.util.List;

public class ProductionLine {
    private final List<Machine> lineItems = new ArrayList<>();

    public ProductionLine() {
        lineItems.add(new CutMachine(1));
        lineItems.add(new CurveMachine(1));
        lineItems.add(new WeldMachine(1));
        lineItems.add(new TestMachine(1));
        lineItems.add(new PaintMachine(1));
        lineItems.add(new PackMachine(1));
    }
}
