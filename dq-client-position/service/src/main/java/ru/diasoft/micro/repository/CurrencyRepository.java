package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByISONumber(String isoNumber);

    Optional<Currency> findByCurrencyBrief(String currencyBrief);

    Optional<Currency> findByCurrencyID(Long currencyID);

}
