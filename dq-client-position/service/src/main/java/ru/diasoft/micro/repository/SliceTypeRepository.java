package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.lib.config.aop.Loggable;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author mkushcheva
 * репозиторий для работы с таблицей типы срезов
 */
@Repository
public interface SliceTypeRepository extends JpaRepository<SliceType, Long> {
    @Query(value = "Select coalesce(max(s.priority),0) from SliceType s")
    Integer findMaxPriority();

    @Transactional
    @Modifying
    @Query("update SliceType s set s.sliceName = ?2 , s.priority = ?3 where s.sliceTypeID in (?1)")
    void updateSliceNameAndPriorityByID(Long sliceTypeID, String sliceName, Integer Priority);

    @Transactional
    @Modifying
    @Query("update SliceType s set s.sliceName = ?2 where s.sliceTypeID in (?1)")
    void updateSliceNameByID(Long sliceTypeID, String sliceName);

    @Transactional
    @Modifying
    @Query("update SliceType s set s.priority = ?2 where s.sliceTypeID in (?1)")
    void updateSlicePriorityByID(Long sliceTypeID, Integer Priority);

    @Query("select s from SliceType s where s.type = 1 or (s.customID = ?1 and s.type= 2) order by s.priority asc, s.sliceName asc")
    List<SliceType> findByCustomIDAndSystemSlice(Long CustomID);
}
