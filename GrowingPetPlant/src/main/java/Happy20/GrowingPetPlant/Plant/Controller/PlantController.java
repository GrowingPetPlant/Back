package Happy20.GrowingPetPlant.Plant.Controller;

import Happy20.GrowingPetPlant.Plant.Domain.Plant;
import Happy20.GrowingPetPlant.Plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantRepository plantRepository;

    @GetMapping("")
    public ResponseEntity<Plant> GetPlantInfo(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        Plant plant = plantRepository.findPlantByPlantNumber(plantNumber);

        if (plant == null)
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

        return (ResponseEntity.status(HttpStatus.OK).body(plant));
    }
}
