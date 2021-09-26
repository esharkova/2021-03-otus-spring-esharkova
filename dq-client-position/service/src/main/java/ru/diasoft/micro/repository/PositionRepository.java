package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.service.PositionProjection;

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

    List<Position> findByFixFlagAndPositionDateKind(Integer fixFlag, Integer positionDateKind);

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

    @Query("select distinct assetID from Position")
    List<Long> findDistinctAssetID();


    @Query(value = " select pos.positionID" +
            "        as positionID," +
            "        pos.positionDateKind" +
            "        as positionDateKind," +
            "        pos.outRest" +
            "        as outRest," +
            "        CASE" +
            "          WHEN pos.assetType  = 1 THEN cur.currencyName" +
            "          WHEN pos.assetType  = 2 THEN sec.securityCode" +
            "          ELSE 'not defined'" +
            "        END" +
            "        as assetName" +
            "   from Position as pos" +
            " JOIN PortfolioStructure as portf" +
            "  ON portf.portfolioStructureID = pos.portfolioStructureID" +
            " JOIN BrokAccount as brok" +
            "  ON brok.brokAccount = portf.brokAccount" +
            "  and brok.agreementID = portf.brokerAgreementID" +
            "  and ((brok.uccMoEx = :code and pos.assetType = 1) or (brok.tradingAccount = :code and pos.assetType = 2))" +
            " LEFT OUTER JOIN Currency as cur" +
            "  ON pos.assetID = cur.currencyID" +
            " LEFT OUTER JOIN Security as sec" +
            "  ON pos.assetID = sec.securityID" +
            "  where portf.brokerAgreementID = :objectPosID and pos.positionDateKind IN(2, 3, 4)")
    List<PositionProjection> getPositionLimit(@Param("objectPosID") Long objectPosID,
                                              @Param("code") String code);
}
