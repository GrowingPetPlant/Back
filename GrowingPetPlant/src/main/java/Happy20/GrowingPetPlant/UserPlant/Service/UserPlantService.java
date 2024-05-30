package Happy20.GrowingPetPlant.UserPlant.Service;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserPlantService {

    private final UserPlantRepository userPlantRepository;

    @Transactional
    public UserPlant validateUserPlant(Long userNumber) {
        return userPlantRepository.findByUserNumber(userNumber);
    }

    @Transactional
    public List<Long> findAllPlantNumber() {
        return userPlantRepository.findAllPlantNumber();
    }
}
