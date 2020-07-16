package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.ProductType;

public class PackMachine implements Machine {
    private final int id;

    public PackMachine(int id) {
        this.id = id;
    }

    @Override
    public double getSpeed(ProductType productType) {
        double speed;
        switch (productType) {
            case GYB:
                speed = 1 / 10.;
                break;
            case FB:
                speed = 1 / 15.;
                break;
            case SB:
                speed = 1 / 12.;
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
