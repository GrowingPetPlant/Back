package Happy20.GrowingPetPlant.UserPlant.Port;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPlantRepository extends JpaRepository<UserPlant, Long> {
}