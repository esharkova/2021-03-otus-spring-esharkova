package ru.diasoft.micro.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.generator.CustomPositionGenerator;
import ru.diasoft.micro.generator.SliceTypeGenerator;
import ru.diasoft.micro.repository.CustomPositionRepository;
import ru.diasoft.micro.repository.PositionRepository;
import ru.diasoft.micro.repository.SliceTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CustomPositionServiceTest {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CustomPositionRepository customPositionPepository;

    @Autowired
    private SliceTypeRepository sliceTypePepository;

    private CustomPositionServiceImpl customPositionService;

    @BeforeEach
    void setUp() {
        customPositionService = new CustomPositionServiceImpl(customPositionPepository);
    }

    @AfterEach
    void tearDown() {
        positionRepository.deleteAll();
        customPositionPepository.deleteAll();
        sliceTypePepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void fillCustomPositionTableCacheTest() {
        SliceType sliceTypeID = sliceTypePepository.saveAll(SliceTypeGenerator.getSliceTypeWithValueListForCustomPositionTest())
                .get(0);
        positionRepository.saveAll(CustomPositionGenerator.getPositionListForCustomPositionTest());
        customPositionService.fillCustomPositionTableCache(CustomPositionGenerator.CUSTOMID2, sliceTypeID);
        assertThat(customPositionPepository.findAll().size()).isEqualTo(2);
    }
}
