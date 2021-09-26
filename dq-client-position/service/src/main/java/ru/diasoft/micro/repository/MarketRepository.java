package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
}
