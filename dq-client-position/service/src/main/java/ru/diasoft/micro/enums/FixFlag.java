package ru.diasoft.micro.enums;

public enum FixFlag {
    NOT_FIXED(0),
    FIXED(1);

    private Integer value;

    FixFlag(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
