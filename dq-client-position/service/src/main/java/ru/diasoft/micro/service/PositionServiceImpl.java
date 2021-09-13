package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.repository.PositionRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Primary
@Loggable
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository repository;

    @Override
    public Position createPosition(Position position) {

        return repository.save(position);
    }

    @Override
    public void deletePosition(Position position) {
        repository.delete(position);
    }

    @Override
    public List<Position> findByFixFlagAndPositionDateKindNot(Integer fixFlag, Integer positionDateKind) {
        return repository.findByFixFlagAndPositionDateKindNot(fixFlag, positionDateKind);
    }

    @Override
    public List<Position> findByFixFlagAndPositionDateKindGreaterThan(Integer fixFlag, Integer positionDateKind) {
        return repository.findByFixFlagAndPositionDateKindGreaterThan(fixFlag, positionDateKind);
    }

    @Override
    public List<Position> savePositionList(List<Position> positionList) {

        return repository.saveAll(positionList);
    }

    @Override
    public void setFixFlag(List<Long> positionIDList, Integer fixFlag, ZonedDateTime fixPositionDate) {
        repository.setFixFlag(positionIDList, fixFlag, fixPositionDate);
    }

    @Override
    public Integer getNextPositionDateKind(Integer positionDateKind) {
        return positionDateKind+1;
    }

    @Override
    public Integer getPreviousPositionDateKind(Integer positionDateKind) {
        return positionDateKind-1;
    }

    @Override
    public Boolean checkPositionDateKind(Integer positionDateKind) {
        Boolean isPresent = Arrays.stream(PositionDateKind.values())
                .anyMatch(element -> element.getValue()==positionDateKind);

        return isPresent;

    }



}
