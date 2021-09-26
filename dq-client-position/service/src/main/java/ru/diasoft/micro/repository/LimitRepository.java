package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.Limit;

import java.time.ZonedDateTime;


@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

    Limit findLimitsByObjectPosTypeAndObjectPosIdAndRequestTypeAndRequestDate(int objectPosType, Long objectPosId, int requestType, ZonedDateTime requestDate);
}
