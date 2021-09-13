package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.ModelOperation;
import ru.diasoft.micro.repository.ModelOperationRepository;
import ru.diasoft.micro.service.model.ModelOperationService;
import ru.diasoft.micro.service.model.ModelOperationServiceImpl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ModelOperationServiceImplTest {
    public static final String CLIENT_CODE = "clientCode";
    public static final String FIN_INSTRUMENT_CODE = "finInstrumentCode";
    public static final int DIRECTION = 1;
    public static final String ISO_NUMBER = "810";
    public static final String OPERATION_NUMBER = "operationNumber";

    private ModelOperation modelOperation;
    private List<ModelOperation> modelOperationList;
    private ModelOperation createdModelOperation;
    private ModelOperationService modelOperationService;

    @Mock
    private ModelOperationRepository modelOperationRepository;

    @BeforeEach
    void setUp() {
        modelOperationService = new ModelOperationServiceImpl(modelOperationRepository);

        modelOperation = ModelOperation.builder()
                .counterpartCode("counterpartCode")
                .clientCode(CLIENT_CODE)
                .direction(DIRECTION)
                .securityCode(FIN_INSTRUMENT_CODE)
                .isoNumber(ISO_NUMBER)
                .quantity(BigDecimal.ONE)
                .amount(BigDecimal.TEN)
                .price(BigDecimal.ONE)
                .operationDate(ZonedDateTime.now())
                .operationNumber(OPERATION_NUMBER)
                .build();


    }



    @Test
    void saveModelOperationList() {

    }

    @Test
    void saveModelOperation() {
        when(modelOperationRepository.save(any())).thenReturn(modelOperation);

        createdModelOperation =  modelOperationRepository.save(modelOperation);

        assertThat(createdModelOperation).isEqualToComparingOnlyGivenFields(modelOperation, "operationID");

    }
}