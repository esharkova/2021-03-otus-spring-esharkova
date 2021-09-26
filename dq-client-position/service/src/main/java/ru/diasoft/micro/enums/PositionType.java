package ru.diasoft.micro.enums;

public enum PositionType {

    SIMPLE_POSITION(1),
    VARIATION_MARGIN(2),
    GUARANTEE_SECURITY(3);

    private Integer value;

    PositionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
