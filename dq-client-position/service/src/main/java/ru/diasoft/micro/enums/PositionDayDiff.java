package ru.diasoft.micro.enums;

public enum PositionDayDiff {

    DAYS_TM_1(-1),
    DAYS_T0(0),
    DAYS_T1(1),
    DAYS_T2(2),
    DAYS_TX(3);

    private int value;

    PositionDayDiff(int value) {

        this.value = value;
    }

    public int getValue() {

        return value;
    }

}
