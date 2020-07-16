package hu.progmasters;

import hu.progmasters.bike_frame_factory.BikeFrameFactory;
import hu.progmasters.bike_frame_factory.MachineType;
import hu.progmasters.constant_values.ConstantValues;
import hu.progmasters.frame.FrameType;
import hu.progmasters.order.Order;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoDSStar {

    private final BikeFrameFactory bikeFrameFactory = BikeFrameFactory.getInstance();
    private final List<Order> orders = new ArrayList<>();

    public void sortOrdersForMaximumProfit() {
        // TODO not the final position of this method call,
        // TODO should be at the end of the method which reads the input file and fills the order list
        calculateProductionTimesInMinutes();
        Collections.sort(orders);
        long maxProfit = calculateProfitForCurrentOrderList();
        boolean isOptimal = false;
        while (!isOptimal) {
            int numberOfChanges = 0;
            for (int i = 0; i < orders.size() - 1; i++) {
                Order currentOrder = orders.get(i);
                orders.set(i, orders.get(i + 1));
                orders.set(i + 1, currentOrder);
                long currentProfit = calculateProfitForCurrentOrderList();
                if (maxProfit < currentProfit) {
                    maxProfit = currentProfit;
                    numberOfChanges++;
                } else {
                    orders.set(i + 1, orders.get(i));
                    orders.set(i, currentOrder);
                }
            }
            if (numberOfChanges == 0) {
                isOptimal = true;
            }
        }
    }

    long calculateProfitForCurrentOrderList() {
        long totalProfit = 0;
        LocalDateTime startTime = ConstantValues.productionStartDate;
        long totalTimeInMinutes = 0;
        int overlapTimeInMinutes = 0;
        for (Order order : orders) {
            totalTimeInMinutes += order.getTotalProductionTime() - overlapTimeInMinutes;
            overlapTimeInMinutes = order.getProductionTimeBeforeBottleneck();
            LocalDateTime dayFinished = calculateDateFromMinutesPassed(startTime, totalTimeInMinutes);
            long penalty = 0;
            if (dayFinished.isAfter(order.getDeadline())) {
                penalty = (dayFinished.getDayOfYear() - order.getDeadline().getDayOfYear()) * order.getPenaltyPerDay();
            }
            totalProfit += order.getQuantity() * order.getProfitPerPiece() - penalty;
        }
        return totalProfit;
    }

    private LocalDateTime calculateDateFromMinutesPassed(LocalDateTime startTime, long totalTimeInMinutes) {
        LocalDateTime finishTime = startTime;
        LocalDateTime lastWorkDayStarted = startTime;
        if (startTime.getHour() != 6 || startTime.getMinute() != 0) {
            int minutesLeftToday = ((bikeFrameFactory.getFinishHour() - startTime.getHour()) * 60) + (60 - startTime.getMinute());
            totalTimeInMinutes -= minutesLeftToday;
            finishTime = finishTime.plusDays(1);
        }
        int minutesWorkedOneDay = bikeFrameFactory.getWorkHours() * 60;
        while (totalTimeInMinutes > 0) {
            if (!finishTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !finishTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                totalTimeInMinutes -= minutesWorkedOneDay;
                lastWorkDayStarted = finishTime;
            }
            finishTime = finishTime.plusDays(1);
        }
        if (totalTimeInMinutes < 0) {
            finishTime = lastWorkDayStarted.plusMinutes(minutesWorkedOneDay - totalTimeInMinutes);
        }
        return finishTime;
    }

    void calculateProductionTimesInMinutes() {
        for (Order order : orders) {
            calculateTotalProductionTimeForOrderInMinutes(order);
        }
    }

    void calculateTotalProductionTimeForOrderInMinutes(Order order) {
        long totalTime = 0;
        MachineType bottleneckStep = findBottleneckStep(order);
        int bottleneckStepTime = findBottleneckStepTimeByMachineTypeAndOrder(bottleneckStep, order);
        double numberOfMachinesInBottleneck = bikeFrameFactory.getMachinesPerStep().get(bottleneckStep);
        int timeBeforeBottleneck = calculateTimeBeforeBottleneck(bottleneckStep, order);
        order.setProductionTimeBeforeBottleneck(timeBeforeBottleneck);
        totalTime += timeBeforeBottleneck;
        totalTime +=
                (long) Math.ceil((double) (bottleneckStepTime * order.getQuantity())
                        / numberOfMachinesInBottleneck);
        totalTime += calculateTimeAfterBottleneck(bottleneckStep, order);
        order.setTotalProductionTime(totalTime);
    }

    MachineType findBottleneckStep(Order order) {
        MachineType bottleneckStep = MachineType.CUTTER;
        Map<MachineType, Double> machinesPerStep = bikeFrameFactory.getMachinesPerStep();
        calculateAvgStepTimes(order, machinesPerStep);
        double avgTimeOfSlowestStep = machinesPerStep.get(MachineType.CUTTER);
        for (Map.Entry<MachineType, Double> machine : machinesPerStep.entrySet()) {
            if (machine.getValue() > avgTimeOfSlowestStep) {
                bottleneckStep = machine.getKey();
                avgTimeOfSlowestStep = machine.getValue();
            }
        }
        return bottleneckStep;
    }

    int findBottleneckStepTimeByMachineTypeAndOrder(MachineType machineType, Order order) {
        switch (machineType) {
            case BENDER:
                return order.getFrameType().getBendTime();
            case WELDER:
                return order.getFrameType().getWeldTime();
            case TESTER:
                return order.getFrameType().getTestTime();
            case PAINTER:
                return order.getFrameType().getPaintTime();
            case PACKAGER:
                return order.getFrameType().getPackTime();
            default:
                return order.getFrameType().getCutTime();
        }
    }

    private int calculateTimeBeforeBottleneck(MachineType bottleneckStep, Order order) {
        switch (bottleneckStep) {
            case BENDER:
                return order.getFrameType().getCutTime();
            case WELDER:
                return order.getFrameType().getCutTime()
                        + order.getFrameType().getBendTime();
            case TESTER:
                return order.getFrameType().getCutTime()
                        + order.getFrameType().getBendTime()
                        + order.getFrameType().getWeldTime();
            case PAINTER:
                return order.getFrameType().getCutTime()
                        + order.getFrameType().getBendTime()
                        + order.getFrameType().getWeldTime()
                        + order.getFrameType().getTestTime();
            case PACKAGER:
                return order.getFrameType().getCutTime()
                        + order.getFrameType().getBendTime()
                        + order.getFrameType().getWeldTime()
                        + order.getFrameType().getTestTime()
                        + order.getFrameType().getPaintTime();
            default:
                return 0;
        }
    }

    private int calculateTimeAfterBottleneck(MachineType bottleneckStep, Order order) {
        switch (bottleneckStep) {
            case CUTTER:
                return order.getFrameType().getBendTime()
                        + order.getFrameType().getWeldTime()
                        + order.getFrameType().getTestTime()
                        + order.getFrameType().getPaintTime()
                        + order.getFrameType().getPackTime();
            case BENDER:
                return order.getFrameType().getWeldTime()
                        + order.getFrameType().getTestTime()
                        + order.getFrameType().getPaintTime()
                        + order.getFrameType().getPackTime();
            case WELDER:
                return order.getFrameType().getTestTime()
                        + order.getFrameType().getPaintTime()
                        + order.getFrameType().getPackTime();
            case TESTER:
                return order.getFrameType().getPaintTime()
                        + order.getFrameType().getPackTime();
            case PAINTER:
                return order.getFrameType().getPackTime();
            default:
                return 0;
        }
    }

    private void calculateAvgStepTimes(Order order, Map<MachineType, Double> machinesPerStep) {
        machinesPerStep.replace(MachineType.CUTTER, order.getFrameType().getCutTime() / machinesPerStep.get(MachineType.CUTTER));
        machinesPerStep.replace(MachineType.BENDER, order.getFrameType().getBendTime() / machinesPerStep.get(MachineType.BENDER));
        machinesPerStep.replace(MachineType.WELDER, order.getFrameType().getWeldTime() / machinesPerStep.get(MachineType.WELDER));
        machinesPerStep.replace(MachineType.TESTER, order.getFrameType().getTestTime() / machinesPerStep.get(MachineType.TESTER));
        machinesPerStep.replace(MachineType.PAINTER, order.getFrameType().getPaintTime() / machinesPerStep.get(MachineType.PAINTER));
        machinesPerStep.replace(MachineType.PACKAGER, order.getFrameType().getPackTime() / machinesPerStep.get(MachineType.PACKAGER));
    }

    public void addOrder(String orderId, FrameType frameType, int quantity, LocalDateTime deadline, int profitPerPiece, int penaltyPerDay) {
        orders.add(new Order(orderId, frameType, quantity, deadline, profitPerPiece, penaltyPerDay));
    }
}
