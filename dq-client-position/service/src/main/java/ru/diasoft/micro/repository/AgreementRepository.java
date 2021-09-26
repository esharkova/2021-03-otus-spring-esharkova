package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.Agreement;

import java.util.Optional;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    Optional<Agreement> findByAgreementCode(String agreementCode);

    Optional<Agreement> findByClientID(Long clientID);
}
