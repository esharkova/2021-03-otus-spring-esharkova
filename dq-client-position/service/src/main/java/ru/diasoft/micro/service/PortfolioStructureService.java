package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.BrokAccount;
import ru.diasoft.micro.domain.Portfolio;
import ru.diasoft.micro.domain.PortfolioStructure;
import ru.diasoft.micro.exception.SearchPortfolioStructureException;
import ru.diasoft.micro.model.BrokerAgreementDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author esharkova
 * интерфейс работы со структурой портфеля
 */

public interface PortfolioStructureService {

    public Optional<PortfolioStructure> findByParam(Map<String, String> portfolioParam);

    public PortfolioStructure findByPortfolioStructureID(Long portfolioStructureID);

    /**
     * Метод разбора сообщения по договору (событие OnAfterSOrd_MassProcess - Событие по заявкам на обслуживание для выгрузки информации
     * по сформированным и измененным объектам)
     *
     * @param BrokerAgreementDto - Событие по заявке
     */
    List<PortfolioStructure> transformDtoToPortfolioStructure(BrokerAgreementDto brokerAgreementDto) throws SearchPortfolioStructureException;

    List<BrokAccount> transformDtoToBrokAccount(BrokerAgreementDto brokerAgreementDto) throws SearchPortfolioStructureException;

    public List<PortfolioStructure> saveAll(List<PortfolioStructure> portfolioStructureList);

    public List<BrokAccount> saveAllBrokAccount(List<BrokAccount> brokAccountList);

    public List<Portfolio> saveAllPortfolio(List<Portfolio> portfolioList);

}
