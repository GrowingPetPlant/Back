package Happy20.GrowingPetPlant.UserPlant.Service.Port;

import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface UserPlantRepository extends JpaRepository<UserPlant, Long> {
    Boolean existsByUserAndUserPlantName(User user, String userPlantName);
    List<UserPlant> findAllByUser(User user);
    UserPlant findByUserPlantNumber(Long userPlantNumber);

    UserPlant findFirstByUserOrderByUserPlantNumberAsc(User user);

    // 식물 번호로 물 준 날짜 리스트 리턴
    @Query("SELECT DISTINCT s.userPlant FROM Status s")
    List<Long> findAllByuserPlant();
}
