package Happy20.GrowingPetPlant.Status.Service;

import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final UserPlantRepository userPlantRepository;

    // 식물 최근 온도 표시
    @Transactional
    public Long recentPlantTemp(Long plantNumber) {
        Status status = statusRepository.findRecentStatusByPlantNumber(plantNumber);

        return status.getTemperature();
    }

    // 식물 최근 습도 표시
    @Transactional
    public Long recentPlantMoisture(Long plantNumber) {
        Status status = statusRepository.findRecentStatusByPlantNumber(plantNumber);

        return status.getMoisture();
    }

    // 식물 최근 토양 습도 표시
    @Transactional
    public Long recentPlantHumidity(Long plantNumber) {
        Status status = statusRepository.findRecentStatusByPlantNumber(plantNumber);

        return status.getHumidity();
    }

    // 식물 이름 표시
    @Transactional
    public String getPlantName(Long plantNumber) {
        UserPlant userPlant = userPlantRepository.findByPlantNumber(plantNumber);
        return userPlant.getPlantName();
    }

    // 식물 물 준 날짜 가져오기
    @Transactional
    public List<LocalDate> getWateringDate(Long plantNumber) {
        List<Date> DateList = statusRepository.findWateringByPlantNumber(plantNumber);

        List<LocalDate> localDateList = new ArrayList<>();

        // Date를 LocalDate로 변환
        // 변환 안할 경우 제대로 출력 X
        for (Date date : DateList) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localDateList.add(localDate);
        }

        return localDateList;
    }
}
