package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.ProductType;

public class TestMachine implements Machine {
    private final int id;

    public TestMachine(int id) {
        this.id = id;
    }

    @Override
    public double getSpeed(ProductType productType) {
        double speed;
        switch (productType) {
            case GYB:
                speed = 1 / 5.;
                break;
            case FB:
                speed = 1 / 5.;
                break;
            case SB:
                speed = 1 / 5.;
                break;
            default:
                speed = 0;
        }
        return speed;
    }

    @Override
    public int getId() {
        return id;
    }
}
