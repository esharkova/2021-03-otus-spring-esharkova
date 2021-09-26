package ru.diasoft.micro.enums;

public enum SliceTypeEnum {
    SYSTEM(1),
    CUSTOM(2);

    private Integer value;

    SliceTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
