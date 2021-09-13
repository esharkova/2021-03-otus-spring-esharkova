package ru.diasoft.micro.enums;

public enum AssetType {

    MONEY(1),
    SECURITY(2),
    METAL(3),
    DERIVATIVE(4),
    COMMODITY(5);


    private Integer value;

    AssetType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
