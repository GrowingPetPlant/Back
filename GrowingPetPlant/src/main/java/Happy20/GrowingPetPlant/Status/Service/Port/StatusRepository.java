package Happy20.GrowingPetPlant.Status.Service.Port;

import Happy20.GrowingPetPlant.Status.Domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public interface StatusRepository extends JpaRepository<Status, Long> {
    // 식물 번호로 상태 조회
    Status findByPlantNumber(Long plantNumber);

    // 식물 번호로 물 준 날짜 조회
    @Query("SELECT s.wateringDate FROM Status s WHERE s.plantNumber = :plantNumber")
    List<Date> findWateringByPlantNumber(@Param("plantNumber")Long plantNumber);

    // 식물 번호로 최근 상태 조회
    @Query("SELECT s FROM Status s WHERE s.plantNumber = :plantNumber ORDER BY s.statusNumber DESC LIMIT 1")
    Status findRecentStatusByPlantNumber(@Param("plantNumber") Long plantNumber);

}