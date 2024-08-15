package Happy20.GrowingPetPlant.Plant;

import Happy20.GrowingPetPlant.Plant.Domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findPlantByPlantType(String plantType);
    Plant findPlantByPlantNumber(Long plantNumber);
}
