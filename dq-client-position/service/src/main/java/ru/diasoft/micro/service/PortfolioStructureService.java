package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.PortfolioStructure;

import java.util.Map;
import java.util.Optional;

/**
 * @author esharkova
 * интерфейс работы со структурой портфеля
 */

public interface PortfolioStructureService {

    public Optional<PortfolioStructure> findByParam(Map<String, String> portfolioParam);

    public PortfolioStructure findByPortfolioStructureID(Long portfolioStructureID);

}
