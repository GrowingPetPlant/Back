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

    // 0으로 초기화 된 그래프 데이터 생성(초기값)
    public void createGraph(LocalDate date) {

        // 0으로 초기화 된 그래프 객체 생성
        Graph newGraph = Graph.builder()
                .tempDawn(0)
                .tempMorning(0)
                .tempDay(0)
                .tempNight(0)
                .moistureDawn(0)
                .moistureMorning(0)
                .moistureDay(0)
                .moistureNight(0)
                .humiDawn(0)
                .humiMorning(0)
                .humiDay(0)
                .humiNight(0)
                .date(date)
                .build();

        graphRepository.save(newGraph);
    }

    // 그래프 정보 업데이트(그래프 평균 data)
    public Graph updateGraph(LocalDate today) {

        LocalDateTime startOfToday = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.of(today, LocalTime.MAX);

        List<Status> todayStatus = statusRepository.findAllByStatusCreateTimeBetween(startOfToday, endOfToday);
        Graph update = graphRepository.findByDate(today);

        if (todayStatus.isEmpty() || update == null)
            return (null);

        List<Status> dawn = new ArrayList<>();
        List<Status> morning = new ArrayList<>();
        List<Status> day = new ArrayList<>();
        List<Status> night = new ArrayList<>();

        for (Status status : todayStatus) {
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
        if (!dawn.isEmpty()) {
            tempDawn /= dawn.size();
            moistureDawn /= dawn.size();
            humiDawn /= dawn.size();
        }

        if (!morning.isEmpty()) {
            tempMorning /= morning.size();
            moistureMorning /= morning.size();
            humiMorning /= morning.size();
        }

        if (!day.isEmpty()) {
            tempDay /= day.size();
            moistureDay /= day.size();
            humiDay /= day.size();
        }

        if (!night.isEmpty()) {
            tempNight /= night.size();
            moistureNight /= night.size();
            humiNight /= night.size();
        }

        update.setTempDawn(tempDawn);
        update.setTempMorning(tempMorning);
        update.setTempDay(tempDay);
        update.setTempNight(tempNight);

        update.setHumiDawn(humiDawn);
        update.setHumiMorning(humiMorning);
        update.setHumiDay(humiDay);
        update.setHumiNight(humiNight);

        update.setMoistureDawn(moistureDawn);
        update.setMoistureMorning(moistureMorning);
        update.setMoistureDay(moistureDay);
        update.setMoistureNight(moistureNight);

        graphRepository.save(update);

        return (update);
    }

    // 그래프 display
    public Graph getGraphInfo(LocalDate date)
    {
        return (graphRepository.findByDate(date));
    }
}
