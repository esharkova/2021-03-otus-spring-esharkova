package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.SliceTypeValue;

import java.util.List;

/**
 * @author mkushcheva
 * репозиторий для работы с таблицей значений типа среза
 */
@Repository
public interface SliceTypeValueRepository extends JpaRepository<SliceTypeValue, Long> {
}
