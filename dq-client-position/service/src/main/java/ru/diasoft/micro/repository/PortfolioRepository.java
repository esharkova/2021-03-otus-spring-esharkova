package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.Portfolio;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {

    Optional<Portfolio> findByDepoSubAccountTypeAndClearingPlaceAndBrokAccountAndTradePortfolio(String depoSubAccountType,
                                                                                                String clearingPlace,
                                                                                                String brokAccount,
                                                                                                String tradePortfolio
                                                                                                );
}
