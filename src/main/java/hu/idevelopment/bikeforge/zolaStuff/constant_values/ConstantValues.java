package hu.progmasters.constant_values;

import java.time.LocalDateTime;

public class ConstantValues {
    public static final LocalDateTime productionStartDate = LocalDateTime.of(2020, 7, 20, 6, 0);

    public static final int GYB_CUT_TIME = 5;
    public static final int GYB_BEND_TIME = 10;
    public static final int GYB_WELD_TIME = 8;
    public static final int GYB_TEST_TIME = 5;
    public static final int GYB_PAINT_TIME = 12;
    public static final int GYB_PACK_TIME = 10;

    public static final int FB_CUT_TIME = 8;
    public static final int FB_BEND_TIME = 16;
    public static final int FB_WELD_TIME = 12;
    public static final int FB_TEST_TIME = 5;
    public static final int FB_PAINT_TIME = 20;
    public static final int FB_PACK_TIME = 15;

    public static final int SB_CUT_TIME = 6;
    public static final int SB_BEND_TIME = 15;
    public static final int SB_WELD_TIME = 10;
    public static final int SB_TEST_TIME = 5;
    public static final int SB_PAINT_TIME = 15;
    public static final int SB_PACK_TIME = 12;

    public static final int NUMBER_OF_CUTTERS = 6;
    public static final int NUMBER_OF_BENDERS = 2;
    public static final int NUMBER_OF_WELDERS = 3;
    public static final int NUMBER_OF_TESTERS = 1;
    public static final int NUMBER_OF_PAINTERS = 4;
    public static final int NUMBER_OF_PACKERS = 3;

    public static final int MORNING_SHIFT_BEGINS = 6;
    public static final int AFTERNOON_SHIFT_ENDS = 22;


    private ConstantValues() {
    }

}
