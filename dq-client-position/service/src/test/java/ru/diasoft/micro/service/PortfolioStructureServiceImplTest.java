package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.PortfolioStructure;
import ru.diasoft.micro.generator.ClientGenerator;
import ru.diasoft.micro.generator.PortfolioStructureGenerator;
import ru.diasoft.micro.repository.PortfolioStructureRepository;

import java.util.Optional;

import static io.sundr.shaded.com.github.javaparser.ast.expr.BinaryExpr.Operator.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PortfolioStructureServiceImplTest {

    public static final long ID = 1L;

    @Mock
    private PortfolioStructureRepository portfolioStructureRepository;

    @InjectMocks
    private PortfolioStructureServiceImpl portfolioStructureService;

    PortfolioStructure portfolioStructure;

    @BeforeEach
    void setUp() {
        portfolioStructure = PortfolioStructureGenerator.getPortfolioStructure();
    }


    @Test
    void findByPortfolioStructureID() {
        when(portfolioStructureRepository.findById(ID)).thenReturn((Optional.ofNullable(portfolioStructure)));

        PortfolioStructure findPortfolioStructure = portfolioStructureService.findByPortfolioStructureID(ID);
        assertNotNull(findPortfolioStructure);

        verify(portfolioStructureRepository, times(1)).findById(ID);
    }

}