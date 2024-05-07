package Happy20.GrowingPetPlant.Status;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

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
}
