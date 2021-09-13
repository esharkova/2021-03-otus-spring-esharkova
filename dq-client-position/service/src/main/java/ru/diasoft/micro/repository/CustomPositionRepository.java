package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.domain.DQCLNTPOSCustomPositions;
import ru.diasoft.micro.domain.QDQCLNTPOSCustomPositions;
import ru.diasoft.micro.querydsl.DefaultQuerydslBinderCustomizer;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

/**
 * @author mkushcheva
 * репозиторий для работы с таблицей пользовательская позиция
 */
@Repository
public interface CustomPositionRepository extends
        JpaRepository<DQCLNTPOSCustomPositions, Long>,
        QuerydslPredicateExecutor<DQCLNTPOSCustomPositions>,
        DefaultQuerydslBinderCustomizer<QDQCLNTPOSCustomPositions> {

    @Transactional
    @Modifying
    void deleteByCustomIDAndPositionDateKind(Long customID, Integer positionDateKind);

    @Transactional
    @Modifying
    @Query("insert into DQCLNTPOSCustomPositions (" +
            "           customID," +
            "           calculationDateTime," +
            "           positionDateKind," +
            "           agreementNumber, " +
            "           instrumentName," +
            "           assetType," +
            "           finInstrumentCode," +
            "           isin," +
            "           incomeRest," +
            "           outRest," +
            "           income," +
            "           expense, " +
            "           moneyOvernightKind," +
            "           securityOvernightKind," +
            "           marginLendingKind," +
            "           clientAttr," +
            "           tradingAcc," +
            "           portfolioBrief," +
            "           depoStorageLocation," +
            "           accountClient," +
            "           accountFut)" +
            "    select ?2, " +
            "           ?3, " +
            "           pos.positionDateKind, " +
            "           ag.agreementCode," +
            "           CASE" +
            "             WHEN pos.assetType  = 1 THEN cur.currencyBrief" +
            "             WHEN pos.assetType  = 2 THEN sec.securityName" +
            "             WHEN pos.assetType  = 4 THEN sec. securityBrief" +
            "             ELSE 'not defined'" +
            "           END," +
            "           CASE" +
            "             WHEN pos.assetType  = 1 THEN 'ДС'" +
            "             WHEN pos.assetType  = 2 THEN 'ЦБ'" +
            "             WHEN pos.assetType  = 3 THEN 'Др. мет.'" +
            "             WHEN pos.assetType  = 4 THEN 'Ср. инст.'" +
            "             WHEN pos.assetType  = 5 THEN 'Товар'" +
            "             ELSE 'not defined'" +
            "           END," +
            "           CASE" +
            "             WHEN pos.assetType  = 1 THEN cur.currencyBrief" +
            "             WHEN pos.assetType  = 2 THEN sec.securityCode" +
            "             WHEN pos.assetType  = 4 THEN sec. securityCode" +
            "             ELSE 'not defined'" +
            "           END," +
            "           CASE" +
            "             WHEN pos.assetType = 2 THEN sec.isin" +
            "             ELSE ''" +
            "           END," +
            "           pos.incomeRest," +
            "           pos.outRest," +
            "           pos.income," +
            "           pos.expense," +
            "           ag.moneyOvernight," +
            "           ag.securityOvernight," +
            "           ag.marginLending," +
            "           ag.clientName," +
            "           CASE" +
            "             WHEN pos.assetType  = 2 THEN ag.tradingAccBrief" +
            "             ELSE ''" +
            "           END," +
            "           ps.tradePortfolio," +
            "           CASE" +
            "             WHEN pos.depoAccType  = 1 THEN CONCAT(pos.custody, ' (основ)')" +
            "             ELSE CONCAT(pos.custody, ' (торг)') " +
            "           END," +
            "           ps.brokAccount," +
            "           COALESCE((select max(ps1.clientCode)" +
            "                       from PortfolioStructure as ps1" +
            "                      where ps1.brokAccount = ps.brokAccount" +
            "                        and ps1.market = 2" +
            "                        and ps1.tradePlace = '1'), '' ) as accountFut" +
            "      from Position as pos" +
            "    LEFT OUTER JOIN Agreement as ag" +
            "     ON pos.agreementID = ag.agreementID" +
            "    LEFT OUTER JOIN Security as sec" +
            "     ON pos.assetID = sec.securityID" +
            "    LEFT OUTER JOIN Currency as cur" +
            "     ON pos.assetID = cur.currencyID" +
            "    LEFT OUTER JOIN PortfolioStructure as ps" +
            "     ON pos.portfolioStructureID = ps.portfolioStructureID" +
            "     where pos.positionDateKind = ?1 and pos.fixFlag = 0")
    void insertPositionIntoCustomPosition(Integer posDateKind, Long customID, ZonedDateTime currentDate);
}