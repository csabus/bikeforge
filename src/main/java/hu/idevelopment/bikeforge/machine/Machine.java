package hu.idevelopment.bikeforge.machine;

import hu.idevelopment.bikeforge.ProductType;

public interface Machine {
    double getSpeed(ProductType productType);

    int getId();
}
