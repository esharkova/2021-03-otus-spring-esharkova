package ru.diasoft.micro.service.sliceposition;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.generator.SliceTypeGenerator;
import ru.diasoft.micro.repository.SliceTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SliceTypeServiceImplTest {
    private SliceTypeServiceImpl sliceTypeService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private DSLogger logger;
    @Mock
    private SliceTypeRepository sliceTypeRepository;

    @BeforeEach
    void setUp() {
        sliceTypeService = new SliceTypeServiceImpl(messageSource, sliceTypeRepository);
    }

    @Test
    @SneakyThrows
    void getSearchConditionTest() {
        sliceTypeService.getSearchCondition(SliceTypeGenerator.getDefaultSliceTypeValueList());
        verify(logger, never()).info(any());

        sliceTypeService.getSearchCondition(null);
        verify(logger, atMost(1)).info(any());
    }

    @Test()
    @DisplayName("получить приоритет")
    public void checkFieldPriorityTest() {
        when(sliceTypeRepository.findMaxPriority()).thenReturn(3);
        Integer priority = sliceTypeService.getNextPriority();
        assertThat(priority).isEqualTo(4);

        verify(sliceTypeRepository, times(1)).findMaxPriority();
    }

    @Test()
    @DisplayName("проверить тип среза удачно")
    public void checkSliceTypeTest_success() {
        assertTrue(sliceTypeService.checkSliceType(1));
    }

    @Test()
    @DisplayName("проверить тип среза не удачно")
    public void checkSliceTypeTest_failure() {
        assertFalse(sliceTypeService.checkSliceType(5));
    }

    @Test()
    @DisplayName("удалить срез по id")
    public void deleteSliceBySliceTypeIDTest() {
        sliceTypeService.deleteById(1L);
        verify(sliceTypeRepository, times(1)).deleteById(any());
    }

    @Test()
    @DisplayName("обновить имя среза и позицию")
    public void updateSliceNameAndPriorityTest() {
        sliceTypeService.updateById(1L, "Name", 1);
        verify(sliceTypeRepository, times(1)).updateSliceNameAndPriorityByID(1L, "Name", 1);
    }

    @Test()
    @DisplayName("обновить имя среза, позицию нет")
    public void updateSliceNameTest() {
        sliceTypeService.updateById(1L, "Name", null);
        verify(sliceTypeRepository, times(1)).updateSliceNameByID(1L, "Name");
    }

    @Test()
    @DisplayName("обновить позицию, имя нет")
    public void updatePriorityTest() {
        sliceTypeService.updateById(1L, null, 1);
        verify(sliceTypeRepository, times(1)).updateSlicePriorityByID(1L, 1);
    }

    @Test()
    @DisplayName("поиск среза по пользователю")
    public void findByCustomIdTest() {
        when(sliceTypeRepository.findByCustomIDAndSystemSlice(1L)).thenReturn(any());
        sliceTypeService.findByCustomId(1L);
        verify(sliceTypeRepository, times(1)).findByCustomIDAndSystemSlice(1L);
    }
}