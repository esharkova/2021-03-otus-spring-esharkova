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
            "           ag.brAgrNumber," +
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
            "           ba.moneyOvernight," +
            "           ba.securityOvernight," +
            "           ba.marginLending," +
            "           ag.clientName," +
            "           CASE" +
            "             WHEN pos.assetType  = 2 THEN ba.tradingAccount" +
            "             ELSE ''" +
            "           END," +
            "           ps.tradePortfolio," +
            "           CASE" +
            "             WHEN pos.depoAccType  = 1 THEN CONCAT(ps.clearingPlace, ' (основ)')" +
            "             ELSE CONCAT(ps.clearingPlace, ' (торг)') " +
            "           END," +
            "           ps.brokAccount," +
            "           ba.derivAccount" +
            "      from Position as pos" +
            "    LEFT OUTER JOIN Security as sec" +
            "     ON pos.assetID = sec.securityID" +
            "    LEFT OUTER JOIN Currency as cur" +
            "     ON pos.assetID = cur.currencyID" +
            "    LEFT OUTER JOIN PortfolioStructure as ps" +
            "     ON pos.portfolioStructureID = ps.portfolioStructureID" +
            "    LEFT OUTER JOIN Agreement as ag" +
            "     ON ag.agreementID = ps.brokerAgreementID" +
            "    LEFT OUTER JOIN BrokAccount as ba" +
            "     ON ba.brokAccount = ps.brokAccount" +
            "    where pos.positionDateKind = ?1 and pos.fixFlag = 0")
    void insertPositionIntoCustomPosition(Integer posDateKind, Long customID, ZonedDateTime currentDate);
}