package ru.diasoft.micro.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.generator.SliceTypeGenerator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SliceTypeRepositoryTest {
    @Autowired
    private SliceTypeRepository repository;
    @Autowired
    private SliceTypeValueRepository valueRepository;


    @AfterEach
    void repositoryDelete() {
        repository.deleteAll();
        valueRepository.deleteAll();
    }

    @BeforeEach
    void repositorySave() {
        repository.save(SliceTypeGenerator.getSliceTypeWithValueList());
    }

    @Test
    void createSliceTypeTest() {
        assertThat(repository.findAll().size()).isEqualTo(1);
        assertThat(valueRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void deleteSliceTypeTest() {
        repository.deleteById(repository.findAll().get(0).getSliceTypeID());
        assertThat(repository.findAll().size()).isEqualTo(0);
        assertThat(valueRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("найти максимальный приоритет")
    void findMaxPriorityTest() {
        repository.saveAll(SliceTypeGenerator.getSliceTypeWithValueListForCustomPositionTest());
        assertThat(repository.findMaxPriority()).isEqualTo(15);
    }

    @Test
    @DisplayName("найти максимальный приоритет если нет записей в таблице")
    void findMaxPriorityNullTest() {
        assertThat(repository.findMaxPriority()).isEqualTo(0);
    }

    @Test
    @DisplayName("найти тип среза по пользователю")
    void findfindByCustomIDTest() {
        repository.saveAll(SliceTypeGenerator.getSliceTypeWithValueListForCustomPositionTest());
        List<SliceType> sliceTypes = repository.findByCustomIDAndSystemSlice(SliceTypeGenerator.CUSTOM_ID1);
        assertThat(sliceTypes.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Изменения имени и приоритета")
    void updateSliceNameAndPriorityTest() {
        SliceType sliceType = repository.findAll().get(0);
        Long sliceTypeID = sliceType.getSliceTypeID();
        String sliceNameNew = "NewName";
        Integer priorityNew = 100;

        repository.updateSliceNameAndPriorityByID(sliceTypeID, sliceNameNew, priorityNew);
        SliceType sliceTypeNew = repository.findById(sliceTypeID).get();
        assertThat(sliceTypeNew.getSliceName()).isEqualTo(sliceNameNew);
        assertThat(sliceTypeNew.getPriority()).isEqualTo(priorityNew);
    }

    @Test
    @DisplayName("Изменения имени, приоритета нет")
    void updateSliceNameTest() {
        SliceType sliceType = repository.findAll().get(0);
        Long sliceTypeID = sliceType.getSliceTypeID();
        Integer slicePriorityOld = sliceType.getPriority();
        String sliceNameNew = "NewName";

        repository.updateSliceNameByID(sliceTypeID, sliceNameNew);
        SliceType sliceTypeNew = repository.findById(sliceTypeID).get();
        assertThat(sliceTypeNew.getSliceName()).isEqualTo(sliceNameNew);
        assertThat(sliceTypeNew.getPriority()).isEqualTo(slicePriorityOld);
    }

    @Test
    @DisplayName("Изменения приоритета, имени нет")
    void updateSlicePriorityTest() {
        SliceType sliceType = repository.findAll().get(0);
        Long sliceTypeID = sliceType.getSliceTypeID();
        String sliceNameOld = sliceType.getSliceName();
        Integer priorityNew = 100;

        repository.updateSlicePriorityByID(sliceTypeID, priorityNew);
        SliceType sliceTypeNew = repository.findById(sliceTypeID).get();
        assertThat(sliceTypeNew.getSliceName()).isEqualTo(sliceNameOld);
        assertThat(sliceTypeNew.getPriority()).isEqualTo(priorityNew);
    }

}
