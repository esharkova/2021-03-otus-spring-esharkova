package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.Security;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    Optional<Security> findBySecurityCode(String securityCode);

    Optional<Security> findByIsin(String isin);

    @Transactional
    @Modifying
    @Query("delete from Security")
    void deleteAll();
}
