package ru.diasoft.micro.enums;

public enum PositionDateKind {

    TDETAIL(0, "DetailPosition"),
    TMINUS_1(1, "T-1"),
    T0 (2, "T0"),
    T1 (3, "T+1"),
    T2 (4, "T+2"),
    TX (5, "Tx");

    private Integer value;
    private String positionName;

    PositionDateKind(Integer value, String positionName) {

        this.value = value;
        this.positionName  = positionName;
    }

    public Integer getValue() {

        return value;
    }

    public String getPositionName() {
        return positionName;
    }
}
