package Happy20.GrowingPetPlant.Status.Service;

import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final UserPlantRepository userPlantRepository;

    // 상태 생성
    public void createStatus(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        Status newStatus = Status.builder()
                .moisture(recentStatus.getMoisture())
                .temperature(recentStatus.getTemperature())
                .humidity(recentStatus.getHumidity())
                .light(recentStatus.getLight())
                .fan(recentStatus.getFan())
                .watering(false)
                .userPlant(userPlant)
                .build();

        statusRepository.save(newStatus);
    }

    // 식물 최근 온도 표시
    @Transactional
    public Double recentPlantTemp(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        return recentStatus.getTemperature();
    }

    // 식물 최근 습도 표시
    @Transactional
    public Double recentPlantMoisture(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        return recentStatus.getMoisture();
    }

    // 식물 최근 토양 습도 표시
    @Transactional
    public Double recentPlantHumidity(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        return recentStatus.getHumidity();
    }

    // 식물 이름 표시
    @Transactional
    public String getPlantName(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);

        return userPlant.getUserPlantName();
    }

    // 식물 물 준 날짜 가져오기
    @Transactional
    public List<LocalDate> getWateringDate(Long userPlantNumber) {

//        List<LocalDate> localDateList = new ArrayList<>();
//
//        // Date를 LocalDate로 변환
//        // 변환 안할 경우 제대로 출력 X
//        for (Date date : DateList) {
//            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            localDateList.add(localDate);
//        }

        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        return statusRepository.findWateringByPlantNumber(userPlant);
    }

    @Transactional
    public int getGrowingDays(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        long daysBetween = ChronoUnit.DAYS.between(userPlant.getGrowingDate(), LocalDate.now());
        return (int) (daysBetween+1);   //1일부터 시작
    }

}
