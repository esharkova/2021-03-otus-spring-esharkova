package ru.diasoft.micro.enums;

import java.util.Arrays;

public enum AssetType {

    MONEY(1),
    SECURITY(2),
    METAL(3),
    DERIVATIVE(4),
    COMMODITY(5);


    private final Integer value;

    AssetType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static AssetType getAssetTypeByValue(Integer value) {
        return Arrays.stream(values()).filter(assetType -> assetType.value.equals(value))
                .findFirst()
                .orElse(MONEY);
    }
}
