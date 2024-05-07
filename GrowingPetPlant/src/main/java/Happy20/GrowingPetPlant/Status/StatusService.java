package Happy20.GrowingPetPlant.Status;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final UserPlantRepository userPlantRepository;

    @Transactional
    public Long getPlantTemp(Long plantNumber) {
        Status status = statusRepository.findByPlantNumber(plantNumber); // 아이디로 사용자 조회
        return status.getTemperature();
    }

    @Transactional
    public Long getPlantMoisture(Long plantNumber) {
        Status status = statusRepository.findByPlantNumber(plantNumber); // 아이디로 사용자 조회
        return status.getMoisture();
    }

    @Transactional
    public Long getPlantHumidity(Long plantNumber) {
        Status status = statusRepository.findByPlantNumber(plantNumber); // 아이디로 사용자 조회
        return status.getHumidity();
    }

    @Transactional
    public String getPlantName(Long plantNumber) {
        UserPlant userPlant = userPlantRepository.findByPlantNumber(plantNumber); // 아이디로 사용자 조회
        return userPlant.getPlantName();
    }
}
