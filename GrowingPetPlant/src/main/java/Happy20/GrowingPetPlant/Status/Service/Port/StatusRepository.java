package Happy20.GrowingPetPlant.Status.Service.Port;

import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface StatusRepository extends JpaRepository<Status, Long> {

    // 식물 번호로 물 준 날짜 리스트 리턴
    @Query("SELECT DISTINCT s.createTime FROM Status s WHERE s.userPlant = :userPlant AND s.watering = true")
    List<LocalDate> findWateringByPlantNumber(@Param("userPlant") UserPlant userPlant);

    // 식물 번호로 최근 상태 1개 조회
    @Query("SELECT s FROM Status s WHERE s.userPlant = :userPlant ORDER BY s.statusNumber DESC LIMIT 1")
    Status findRecentStatusByUserPlant(@Param("userPlant") UserPlant userPlant);

    // 특정 날짜에 생성된 상태 리스트 리턴
    @Query("SELECT s FROM Status s WHERE s.createTime BETWEEN :start AND :end")
    List<Status> findAllByStatusCreateTimeBetween(        @Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end);
}