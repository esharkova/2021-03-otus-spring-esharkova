package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.PortfolioStructure;

import java.util.Optional;

@Repository
public interface PortfolioStructureRepository extends JpaRepository<PortfolioStructure,Long> {

    Optional<PortfolioStructure> findByAgreementIDAndTradePortfolio(Long agreementID, String tradePortfolio);

    Optional<PortfolioStructure> findByAgreementIDAndClientCode(Long agreementID, String clientCode);

    Optional<PortfolioStructure> findByAgreementIDAndTradePlaceAndDepoSubAccount(Long agreementID, String tradePlace, String depoSubAccount);

    Optional<PortfolioStructure> findByAgreementIDAndDepoSubAccount(Long agreementID, String depoSubAccount);

}
