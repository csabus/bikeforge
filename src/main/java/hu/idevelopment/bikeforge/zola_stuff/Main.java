package hu.idevelopment.bikeforge.zola_stuff;

import hu.idevelopment.bikeforge.zola_stuff.frame.FrameType;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        RoDSStar roDSStar = new RoDSStar();
        roDSStar.addOrder("MEGR001",
                FrameType.GYB,
                1000,
                LocalDateTime.of(2020, 7, 21, 13, 0),
                1500,
                20000);
        roDSStar.addOrder("MEGR002",
                FrameType.FB,
                1200,
                LocalDateTime.of(2020, 7, 22, 8, 0),
                3500,
                50000);
        roDSStar.addOrder("MEGR003",
                FrameType.SB,
                250,
                LocalDateTime.of(2020, 7, 23, 14, 0),
                2400,
                30000);
        roDSStar.addOrder("MEGR004",
                FrameType.FB,
                1000,
                LocalDateTime.of(2020, 7, 30, 8, 0),
                1200,
                100000);
        roDSStar.addOrder("MEGR005",
                FrameType.SB,
                3000,
                LocalDateTime.of(2020, 7, 25, 16, 0),
                2000,
                10000);
        roDSStar.addOrder("MEGR006",
                FrameType.FB,
                450,
                LocalDateTime.of(2020, 7, 28, 12, 0),
                2400,
                25000);
        roDSStar.addOrder("MEGR007",
                FrameType.GYB,
                2810,
                LocalDateTime.of(2020, 7, 30, 14, 0),
                3100,
                35000);
        roDSStar.addOrder("MEGR008",
                FrameType.GYB,
                420,
                LocalDateTime.of(2020, 8, 5, 10, 0),
                3100,
                30000);
        roDSStar.addOrder("MEGR009",
                FrameType.FB,
                50,
                LocalDateTime.of(2020, 8, 15, 18, 0),
                4000,
                80000);
        roDSStar.addOrder("MEGR010",
                FrameType.SB,
                142,
                LocalDateTime.of(2020, 7, 26, 12, 0),
                1350,
                100000);
        roDSStar.addOrder("MEGR011",
                FrameType.SB,
                6000,
                LocalDateTime.of(2020, 7, 30, 8, 0),
                2000,
                10000);
        roDSStar.addOrder("MEGR012",
                FrameType.SB,
                4050,
                LocalDateTime.of(2020, 8, 15, 10, 10),
                1550,
                45000);
        roDSStar.addOrder("MEGR013",
                FrameType.FB,
                1030,
                LocalDateTime.of(2020, 8, 1, 12, 0),
                2500,
                38000);
        roDSStar.addOrder("MEGR014",
                FrameType.GYB,
                1280,
                LocalDateTime.of(2020, 7, 31, 16, 0),
                5000,
                38000);
        roDSStar.addOrder("MEGR015",
                FrameType.SB,
                1000,
                LocalDateTime.of(2020, 8, 5, 12, 45),
                3000,
                200000);


        roDSStar.sortOrdersForMaximumProfit();
        System.out.println("Time elapsed: " + ((double) (System.nanoTime() - start) / (double) (1000000000)) + " s");
    }
}
