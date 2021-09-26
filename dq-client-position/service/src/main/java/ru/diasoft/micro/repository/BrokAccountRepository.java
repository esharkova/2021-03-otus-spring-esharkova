package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.BrokAccount;

@Repository
public interface BrokAccountRepository extends JpaRepository<BrokAccount, Long> {
}
