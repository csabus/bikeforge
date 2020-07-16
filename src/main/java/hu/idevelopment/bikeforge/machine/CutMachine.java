package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.ProductType;

public class CutMachine implements Machine {
    private final int id;

    public CutMachine(int id) {
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
                speed = 1 / 8.;
                break;
            case SB:
                speed = 1 / 6.;
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
