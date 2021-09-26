package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.ModelOperation;
import ru.diasoft.micro.domain.Position;

@Repository
public interface ModelOperationRepository extends JpaRepository<ModelOperation, Long> {
}
