package ru.diasoft.micro.service.sliceposition;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.generator.SliceTypeGenerator;
import ru.diasoft.micro.mdp.lib.utils.exception.APIException;
import ru.diasoft.micro.repository.SliceTypeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class SlicePositionServiceImplTest {
    private SlicePositionServiceImpl slicePositionService;

    @Mock
    private MessageSource messageSource;
    @Mock
    private SliceTypeService sliceTypeService;

    @BeforeEach
    void setUp() {
        slicePositionService = new SlicePositionServiceImpl(sliceTypeService, messageSource);
    }

    private ResponseEntity<?> createSliceType(Integer type){
        return slicePositionService.addDqslice(
                "TestName",
                type,
                1L,
                SliceTypeGenerator.getDefaultDQSliceTypeValueList());
    }

    @Test()
    @DisplayName("Добавление типа среза удачно")
    public void addSliceTypeTest_success() {
        when(sliceTypeService.checkSliceType(1)).thenReturn(true);
        when(sliceTypeService.addSlice(any(), any(), any(), any())).thenReturn(SliceTypeGenerator.getSliceTypeWithValueList());
        ResponseEntity<?> response = createSliceType(1);
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(sliceTypeService, times(1)).checkSliceType(1);
        verify(sliceTypeService, times(1)).addSlice(any(), any(), any(), any());
    }

    @Test()
    @DisplayName("Добавление типа среза генерим исключение")
    public void addSliceTypeTest_APIException() {
        when(sliceTypeService.checkSliceType(5)).thenReturn(false);
        Throwable exception = Assertions.assertThrows(APIException.class, () -> createSliceType(5));
        assertEquals(messageSource.getMessage("customPosSrv.message.IncorrectSliceType",
                null, LocaleContextHolder.getLocale()), exception.getMessage());
        verify(sliceTypeService, times(1)).checkSliceType(5);
        verify(sliceTypeService, times(0)).addSlice(any(), any(), any(), any());
    }

    @Test()
    @DisplayName("Удаление типа среза")
    public void delSliceTypeTest() {
        ResponseEntity<?> response = slicePositionService.deleteDqslice(1L);

        verify(sliceTypeService, times(1)).deleteById(1L);
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test()
    @DisplayName("Изменение типа среза")
    public void updateSliceTypeTest() {
        ResponseEntity<?> response = slicePositionService.updateDqslice(1L, "Name", 1);

        verify(sliceTypeService, times(1)).updateById(1L, "Name", 1);
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test()
    @DisplayName("Получение типа среза по пользователю")
    public void getDqslicessTest() {
        Pageable pageable = PageRequest.of(0, 10);
        when(sliceTypeService.findByCustomId(any())).thenReturn(SliceTypeGenerator.getSliceTypeWithValueListForCustomPositionTest());
        ResponseEntity<?> response = slicePositionService.getDqslicess(pageable, 1L);

        verify(sliceTypeService, times(1)).findByCustomId(1L);
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
