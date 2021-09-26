package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.domain.Market;
import ru.diasoft.micro.enums.MarketKind;
import ru.diasoft.micro.enums.RequestStatus;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.*;
import ru.diasoft.micro.repository.LimitRepository;
import ru.diasoft.micro.repository.PositionRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Loggable
@RequiredArgsConstructor
public class DailyLimitsServiceImpl implements LimitsTransformService {
    private final LimitRepository limitRepository;
    private final PositionRepository positionRepository;
    private final LimitSupportService limitSupportService;
    public static final int DATE_KIND_CHANGE = 2;

    @Override
    public List<SendLimitDto> transform(Object msg) throws ParseException {
        DailyLimitsDto dailyLimitsDto = (DailyLimitsDto) msg;
        Limit limit = limitSupportService.limitBuilder(dailyLimitsDto);
        List<SendLimitDto> limitDtos = new ArrayList<>();

        if(findDuplicate(limit) != null) {
            limitRepository.delete(findDuplicate(limit));
        }
        limitRepository.save(limit);

        List<Limit> limitList = getUnprocessedRequests();
        if (limitList != null && !limitList.isEmpty()) {
            for (Limit request : limitList) {
                SendLimitDto sendLimitDto = getMainPart(request);
                String trdAccCode = getTrdAccCode(request);
                if (trdAccCode != null && !trdAccCode.isEmpty()) {
                    sendLimitDto.setSecuritiesLimits(buildSecurityPart(request, trdAccCode));
                }
                if (request.getMarketList().stream().anyMatch(x -> x.getMarket().equals(MarketKind.MONEY.getValue()))) {
                    sendLimitDto.setMoneyLimits(buildMoneyPart(request));
                }
                sqlResultsCheck(sendLimitDto, request , limitDtos);
            }
        }
        return limitDtos;
    }

    // Общая часть
    public SendLimitDto getMainPart(Limit request) {
        return SendLimitDto.builder()
                .objectPosType(request.getObjectPosType())
                .objectPosId(request.getObjectPosId())
                .objectPosNumber(request.getObjectPosNumber())
                .requestType(request.getRequestType())
                .clientCode(request.getClientCode())
                .singleAccFlag(request.getSingleAccFlag())
                .markets(getMarketsDtoList(request))
                .build();
    }

    public List<MarketsDto> getMarketsDtoList(Limit request) {
        List<MarketsDto> marketsDtoList = new ArrayList<>();
        for (Market market:request.getMarketList()) {
            marketsDtoList.add(MarketsDto.builder()
                    .market(market.getMarket())
                    .trdAccCode(market.getTrdAccCode())
                    .build());
        }
        return marketsDtoList;
    }

    // получаем торговый код
    public String getTrdAccCode(Limit request) {
        return request.getMarketList().stream()
                .filter(market -> market.getMarket().equals(MarketKind.SECURITY.getValue())).findFirst().get().getTrdAccCode();
    }

    // поиск дубликатов
    private Limit findDuplicate(Limit limit) {
        return limitRepository.findLimitsByObjectPosTypeAndObjectPosIdAndRequestTypeAndRequestDate(limit.getObjectPosType(), limit.getObjectPosId(), limit.getRequestType(), limit.getRequestDate());
    }

    // поиск необработанных запросов
    private List<Limit> getUnprocessedRequests() {
        return limitRepository.findAll().stream()
                .filter(Limit-> Limit.getRequestStatus().equals(RequestStatus.UNPROCESSED_REQUEST.getValue())).collect(Collectors.toList());
    }

    // преобразование datekind -> limitkind
    private int transformPostionDateKindToLimitKind(int positionDateKind) {
        return positionDateKind - DATE_KIND_CHANGE;
    }

    // проверка ответа sql запроса и его добавление
    private void sqlResultsCheck(SendLimitDto sendLimitDto, Limit request, List<SendLimitDto> limitDtos) {
        if ((sendLimitDto.getSecuritiesLimits() == null || sendLimitDto.getSecuritiesLimits().isEmpty())
                && (sendLimitDto.getMoneyLimits() == null || sendLimitDto.getMoneyLimits().isEmpty())) {
            request.setRequestStatus(RequestStatus.ERROR_REQUEST.getValue());
            limitRepository.save(request);
        } else {
            request.setRequestStatus(RequestStatus.PROCESSED_REQUEST.getValue());
            limitRepository.save(request);
            limitDtos.add(sendLimitDto);
        }
    }

    // Добавление или изменение цб
    private void addSecuritiesLimitsDtoToList(PositionProjection position, List<SecuritiesLimitsDto> securitiesLimitsDtoList) {
        if (securitiesLimitsDtoList.stream().noneMatch(x -> (x.getSecCode().equals(position.getAssetName())))
                &&  securitiesLimitsDtoList.stream().noneMatch(x -> (x.getLimitKind().equals(transformPostionDateKindToLimitKind(position.getPositionDateKind()))))) {
            securitiesLimitsDtoList.add(SecuritiesLimitsDto.builder()
                    .limitKind(transformPostionDateKindToLimitKind(position.getPositionDateKind()))
                    .secCode(position.getAssetName())
                    .qtyAmount(position.getOutRest().doubleValue())
                    .build());
        } else {
            SecuritiesLimitsDto changeAmountSecuritiesLimitsDto = securitiesLimitsDtoList.stream()
                    .filter(x -> x.getSecCode().equals(position.getAssetName()) &&
                            x.getLimitKind().equals(transformPostionDateKindToLimitKind(position.getPositionDateKind())))
                    .findFirst().get();
            changeAmountSecuritiesLimitsDto.setQtyAmount(changeAmountSecuritiesLimitsDto.getQtyAmount() + position.getOutRest().doubleValue());
        }
    }

    // Добавление или изменение цб
    private void addMoneyLimitsDtoToList(PositionProjection position, List<MoneyLimitsDto> moneyLimitsDtoList) {
        if (moneyLimitsDtoList.stream().noneMatch(x -> (x.getCurrencyCode().equals(position.getAssetName())))
                &&  moneyLimitsDtoList.stream().noneMatch(x -> (x.getLimitKind().equals(transformPostionDateKindToLimitKind(position.getPositionDateKind()))))) {
            moneyLimitsDtoList.add(MoneyLimitsDto.builder()
                    .limitKind(transformPostionDateKindToLimitKind(position.getPositionDateKind()))
                    .currencyCode(position.getAssetName())
                    .qtyAmount(position.getOutRest().doubleValue())
                    .build());
        } else {
            MoneyLimitsDto changeAmountMoneyLimitsDto = moneyLimitsDtoList.stream()
                    .filter(x -> x.getCurrencyCode().equals(position.getAssetName()) &&
                            x.getLimitKind().equals(transformPostionDateKindToLimitKind(position.getPositionDateKind())))
                    .findFirst().get();
            changeAmountMoneyLimitsDto.setQtyAmount(changeAmountMoneyLimitsDto.getQtyAmount() + position.getOutRest().doubleValue());
        }
    }

    // формирование всей вложенности по цб
    private List<SecuritiesLimitsDto> buildSecurityPart(Limit request, String trdAccCode) {
        List<SecuritiesLimitsDto> securitiesLimitsDtoList = new ArrayList<>();
        List<PositionProjection> securitiesPositionsList = positionRepository
                .getPositionLimit(request.getObjectPosId(), trdAccCode);
        for (PositionProjection position : securitiesPositionsList) {
            addSecuritiesLimitsDtoToList(position, securitiesLimitsDtoList);
        }
        return securitiesLimitsDtoList;
    }

    // формирование всей вложенности по дс
    private List<MoneyLimitsDto> buildMoneyPart(Limit request) {
        List<MoneyLimitsDto> moneyLimitsDtoList = new ArrayList<>();
        List<PositionProjection> moneyPositionsList = positionRepository
                .getPositionLimit(request.getObjectPosId(), request.getClientCode());
        for (PositionProjection position : moneyPositionsList) {
            addMoneyLimitsDtoToList(position, moneyLimitsDtoList);
        }
        return moneyLimitsDtoList;
    }
}
