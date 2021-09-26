package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.PortfolioStructure;

import java.util.Optional;

@Repository
public interface PortfolioStructureRepository extends JpaRepository<PortfolioStructure,Long> {

    Optional<PortfolioStructure> findByBrokerAgreementIDAndTradePortfolio(Long brokerAgreementID, String tradePortfolio);

    Optional<PortfolioStructure> findByTradePortfolioAndBrokAccountAndDepoSubAccountNullAndClearingPlaceNull(String tradePortfolio, String brokAccount);

    Optional<PortfolioStructure> findByBrokerAgreementIDAndClientCode(Long brokerAgreementID, String clientCode);

    Optional<PortfolioStructure> findByTradePlaceAndClientCode(Integer tradePlace, String clientCode);

    Optional<PortfolioStructure> findByBrokerAgreementIDAndTradePlaceAndDepoSubAccount(Long brokerAgreementID, Integer tradePlace, String depoSubAccount);

    Optional<PortfolioStructure> findByBrokerAgreementIDAndDepoSubAccount(Long brokerAgreementID, String depoSubAccount);

    Optional<PortfolioStructure> findByBrokerAgreementIDAndDepoSubAccountAndClearingPlace(Long brokerAgreementID, String depoSubAccount, String clearingPlace);

}
