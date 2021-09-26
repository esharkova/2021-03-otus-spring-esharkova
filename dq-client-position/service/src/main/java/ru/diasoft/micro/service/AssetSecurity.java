package ru.diasoft.micro.service;

import ru.diasoft.micro.enums.AssetType;

/**
 * @author mkushcheva
 * <p>
 * интерфейс Сервиса для работы с объектов Актив.
 */
public interface AssetSecurity {
    /**
     * метод поиска идентификатора актива по Коду актива.
     * Ищем сначала в кэш. если нет то делаем обогащение кэш.
     *
     * @param assetCode - код акива
     * @param assetType - тип акива
     */
    Long getAssetIDByAssetCode(String assetCode, AssetType assetType);
}
