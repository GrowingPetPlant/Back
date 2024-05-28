package Happy20.GrowingPetPlant.Graph.Service;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.Port.GraphRepository;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GraphService {

    private StatusRepository statusRepository;
    private GraphRepository graphRepository;

    // 그래프 평균 data 생성
    public void createGraph(LocalDate today) {
        LocalDate yesterday = today.minusDays(1);

        LocalDateTime startOfYesterday = LocalDateTime.of(yesterday, LocalTime.MIN);
        LocalDateTime endOfYesterday = LocalDateTime.of(yesterday, LocalTime.MAX);

        List<Status> ondDayStatus = statusRepository.findAllByStatusCreateTimeBetween(startOfYesterday, endOfYesterday);

        List<Status> dawn = new ArrayList<>();
        List<Status> morning = new ArrayList<>();
        List<Status> day = new ArrayList<>();
        List<Status> night = new ArrayList<>();;

        for (Status status : ondDayStatus) {
            int hour = status.getCreateTime().getHour();
            if (hour < 6) {
                dawn.add(status);
            } else if (hour < 12) {
                morning.add(status);
            } else if (hour < 18) {
                day.add(status);
            } else {
                night.add(status);
            }
        }

        float tempDawn = 0, tempMorning = 0, tempDay = 0, tempNight = 0,
                moistureDawn = 0, moistureMorning = 0, moistureDay = 0, moistureNight = 0,
                humiDawn = 0, humiMorning = 0, humiDay = 0, humiNight = 0;

        for (Status status : dawn) {
            tempDawn += status.getTemperature();
            moistureDawn += status.getMoisture();
            humiDawn += status.getHumidity();
        }
        for (Status status : morning) {
            tempMorning += status.getTemperature();
            moistureMorning += status.getMoisture();
            humiMorning += status.getHumidity();
        }
        for (Status status : day) {
            tempDay += status.getTemperature();
            moistureDay += status.getMoisture();
            humiDay += status.getHumidity();
        }
        for (Status status : night) {
            tempNight += status.getTemperature();
            moistureNight += status.getMoisture();
            humiNight += status.getHumidity();
        }

        // 평균값 계산
        tempDawn /= dawn.size();
        moistureDawn /= dawn.size();
        humiDawn /= dawn.size();

        tempMorning /= morning.size();
        moistureMorning /= morning.size();
        humiMorning /= morning.size();

        tempDay /= day.size();
        moistureDay /= day.size();
        humiDay /= day.size();

        tempNight /= night.size();
        moistureNight /= night.size();
        humiNight /= night.size();

        // 계산된 평균값으로 그래프 객체 생성
        Graph newGraph = Graph.builder()
                .tempDawn(tempDawn)
                .tempMorning(tempMorning)
                .tempDay(tempDay)
                .tempNight(tempNight)
                .moistureDawn(moistureDawn)
                .moistureMorning(moistureMorning)
                .moistureDay(moistureDay)
                .moistureNight(moistureNight)
                .humiDawn(humiDawn)
                .humiMorning(humiMorning)
                .humiDay(humiDay)
                .humiNight(humiNight)
                .date(today.minusDays(1))
                .build();

        graphRepository.save(newGraph);
    }
}
