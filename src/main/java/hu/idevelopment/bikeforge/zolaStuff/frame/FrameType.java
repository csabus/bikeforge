package hu.progmasters.frame;

import hu.progmasters.constant_values.ConstantValues;

public enum FrameType {

    GYB(ConstantValues.GYB_CUT_TIME, ConstantValues.GYB_BEND_TIME, ConstantValues.GYB_WELD_TIME, ConstantValues.GYB_TEST_TIME, ConstantValues.GYB_PAINT_TIME, ConstantValues.GYB_PACK_TIME),
    FB(ConstantValues.FB_CUT_TIME, ConstantValues.FB_BEND_TIME, ConstantValues.FB_WELD_TIME, ConstantValues.FB_TEST_TIME, ConstantValues.FB_PAINT_TIME, ConstantValues.FB_PACK_TIME),
    SB(ConstantValues.SB_CUT_TIME, ConstantValues.SB_BEND_TIME, ConstantValues.SB_WELD_TIME, ConstantValues.SB_TEST_TIME, ConstantValues.SB_PAINT_TIME, ConstantValues.SB_PACK_TIME);

    private final int cutTime;
    private final int bendTime;
    private final int weldTime;
    private final int testTime;
    private final int paintTime;
    private final int packTime;

    FrameType(int cutTime, int bendTime, int weldTime, int testTime, int paintTime, int packTime) {
        this.cutTime = cutTime;
        this.bendTime = bendTime;
        this.weldTime = weldTime;
        this.testTime = testTime;
        this.paintTime = paintTime;
        this.packTime = packTime;
    }

    public int getCutTime() {
        return cutTime;
    }

    public int getBendTime() {
        return bendTime;
    }

    public int getWeldTime() {
        return weldTime;
    }

    public int getTestTime() {
        return testTime;
    }

    public int getPaintTime() {
        return paintTime;
    }

    public int getPackTime() {
        return packTime;
    }
}
