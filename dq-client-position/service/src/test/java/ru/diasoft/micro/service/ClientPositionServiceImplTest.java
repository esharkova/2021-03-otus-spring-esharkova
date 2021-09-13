package ru.diasoft.micro.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.generator.SliceTypeGenerator;
import ru.diasoft.micro.repository.CustomPositionRepository;
import ru.diasoft.micro.repository.SliceTypeRepository;
import ru.diasoft.micro.service.sliceposition.SliceTypeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ClientPositionServiceImplTest {
    private ClientPositionServiceImpl clientPositionService;

    @Mock
    private CustomPositionRepository customPositionRepository;
    @Mock
    private CustomPositionService customPositionService;
    @Mock
    private SliceTypeRepository sliceTypeRepository;
    @Mock
    private SliceTypeService sliceTypeService;

    @BeforeEach
    void setUp() {
        clientPositionService = new ClientPositionServiceImpl(customPositionRepository, customPositionService, sliceTypeRepository, sliceTypeService);
    }

    @Test
    @SneakyThrows
    @DisplayName("Заполнить таблицу КЭШ")
    public void fillCustomPositionTableCacheTest(){
        Pageable pageable = PageRequest.of(0, 10);
        SliceType sliceType = SliceTypeGenerator.getSliceTypeWithValueList();
        when(sliceTypeRepository.findById(sliceType.getSliceTypeID()))
                .thenReturn(java.util.Optional.of(sliceType));

        ResponseEntity<?> response = clientPositionService.getDqclntpositionslice(pageable, 1L, sliceType.getSliceTypeID(), 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(sliceTypeRepository, times(1)).findById(sliceType.getSliceTypeID());
        verify(customPositionService, times(1)).fillCustomPositionTableCache(1L, sliceType);
        verify(sliceTypeService, times(1)).getSearchCondition(any());
    }

    @Test
    @SneakyThrows
    @DisplayName("Вывести отфильтрованныеданные из КЭШ")
    public void customPositionRepositoryFindTest(){
        Predicate predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        ResponseEntity<?> response = clientPositionService.getDqclientcustompositions(predicate, pageable);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sliceTypeRepository, times(0)).findById(any());
        verify(customPositionService, times(0)).fillCustomPositionTableCache(any(), any());
    }
}
