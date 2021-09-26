package ru.diasoft.micro.service;

import java.math.BigDecimal;

public interface PositionProjection {
    Long getPositionID();
    Integer getPositionDateKind();
    BigDecimal getOutRest();
    String getAssetName();

    Long setPositionID(Long longs);
    Integer setPositionDateKind(Integer integer);
    BigDecimal setOutRest(BigDecimal bigDecimal);
    String setAssetName(String assetName);
}
