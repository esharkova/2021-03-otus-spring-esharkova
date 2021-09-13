package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.Position;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndFixFlag(
            Long portfolioStructureID,
            Long assetID,
            Integer positionType,
            Integer positionDateKind,
            Integer fixFlag
    );


    List<Position> findAll();

    List<Position> findByFixFlagAndPositionDateKindNot(Integer fixFlag, Integer positionDateKind);

    List<Position> findByFixFlagAndPositionDateKindGreaterThan(Integer fixFlag, Integer positionDateKind);

    Position findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndOperationDate(
            Long portfolioStructureID,
            Long assetID,
            Integer positionType,
            Integer positionDateKind,
            LocalDate operationDate
    );

    List<Position> findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndOperationDateGreaterThan(
            Long portfolioStructureID,
            Long assetID,
            Integer positionType,
            Integer positionDateKind,
            LocalDate operationDate
    );
    
    @Transactional
    @Modifying
    @Query("update Position p set p.fixFlag = :fixFlag , p.fixPositionDate = :fixPositionDate where p.positionID in (:positionIDList)")
    void setFixFlag(@Param("positionIDList") List<Long> positionIDList,
                    @Param("fixFlag") Integer fixFlag,
                    @Param("fixPositionDate") ZonedDateTime fixPositionDate);
}
