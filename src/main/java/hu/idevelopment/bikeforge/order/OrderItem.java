package hu.idevelopment.bikeforge.order;

import hu.idevelopment.bikeforge.helper.DateTimeHelper;

import java.time.LocalDateTime;

public class OrderItem {
    private String orderId;
    private final ProductType productType;
    private int processingTime = 0;
    private LocalDateTime startDate = DateTimeHelper.DEFAULT_START_DATE;
    private LocalDateTime finishDate = DateTimeHelper.DEFAULT_START_DATE;

    public OrderItem(String orderId, ProductType productType) {
        this.orderId = orderId;
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        finishDate = DateTimeHelper.addMinutesToDate(startDate, processingTime);
        this.processingTime = processingTime;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productType=" + productType +
                ", processingTime=" + processingTime +
                ", processingDate=" + finishDate +
                "}\n";
    }
}
