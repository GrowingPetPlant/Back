package Happy20.GrowingPetPlant.UserPlant.Service.Port;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPlantRepository extends JpaRepository<UserPlant, Long> {
    UserPlant findByUserNumber(Long userNumber);
    UserPlant findByPlantNumber(Long plantNumber);
}