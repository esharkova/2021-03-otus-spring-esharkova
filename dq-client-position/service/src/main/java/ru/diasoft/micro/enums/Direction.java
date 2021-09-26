package ru.diasoft.micro.enums;

public enum Direction {

    RECEIVE(1),
    PAY(2);

    private Integer value;

    Direction(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
