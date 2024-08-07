package Happy20.GrowingPetPlant.Graph.Service;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.Port.GraphRepository;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
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
    private UserPlantRepository userPlantRepository;

    // 0으로 초기화 된 그래프 데이터 생성(초기값)
    public void createGraph(LocalDate date) {

        // 0으로 초기화 된 그래프 객체 생성
        Graph newGraph = Graph.builder()
                .graphDate(date)
                .build();

        graphRepository.save(newGraph);
    }

    // 그래프 정보 업데이트(그래프 평균 data)
    public boolean updateGraph(LocalDate today) {

        LocalDateTime startOfToday = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.of(today, LocalTime.MAX);

        System.out.println("startOfToday: " + startOfToday);
        System.out.println("endOfToday: " + endOfToday);

        List<Status> todayStatus = statusRepository.findAllByStatusCreateTimeBetween(startOfToday, endOfToday);

        System.out.println("startOfToday: " + todayStatus.get(0));
        System.out.println("endOfToday: " + todayStatus.get(todayStatus.size()-1));

        Graph update = graphRepository.findGraphByGraphDate(today);

        if (todayStatus.isEmpty() || update == null)
            return (false);

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

        System.out.println("Dawn records: " + dawn.size());
        System.out.println("Morning records: " + morning.size());
        System.out.println("Day records: " + day.size());
        System.out.println("Night records: " + night.size());

        Double tempDawn = 0D, tempMorning = 0D, tempDay = 0D, tempNight = 0D,
                moistureDawn = 0D, moistureMorning = 0D, moistureDay = 0D, moistureNight = 0D,
                humiDawn = 0D, humiMorning = 0D, humiDay = 0D, humiNight = 0D;

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

        return (true);
    }

    // 그래프 display
    public Graph getGraphInfo(Long userPlantNumber, LocalDate date)
    {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);

        Graph g = graphRepository.findGraphByGraphDate(date);
        if(g == null)
            return new Graph(userPlant, date);
        else
            return (g);
    }
}
