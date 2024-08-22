package Happy20.GrowingPetPlant.Graph.Service;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.Port.GraphRepository;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
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

    // 매일 자정 0으로 초기화 된 그래프 데이터 생성(초기값)
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void createGraph() {

        List<UserPlant> userPlantList = userPlantRepository.findAll();

        for(UserPlant userPlant : userPlantList)
        {
            // 0으로 초기화 된 그래프 객체 생성
            Graph newGraph = Graph.builder()
                    .userPlant(userPlant)
                    .graphDate(LocalDate.now())
                    .build();

            graphRepository.save(newGraph);
        }
    }

    // 새벽 그래프 정보 업데이트(그래프 평균 data)
    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Seoul")
    public void updateDawnGraph() {

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0));

        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);

        List<UserPlant> userPlantList = userPlantRepository.findAll();

        for (UserPlant userPlant : userPlantList)
        {
            List<Status> dawnStatus = statusRepository.findAllByUserPlantAndCreateTimeBetweenAndTemperatureNotAndHumidityNotAndMoistureNot(userPlant, startTime, endTime, 0.0, 0.0, 0.0);
            Double tempDawn = 0D, moistureDawn = 0D, humiDawn = 0D;

            // 유저 별로 평균 내기
            for (Status status : dawnStatus) {
                tempDawn += status.getTemperature();
                moistureDawn += status.getMoisture();
                humiDawn += status.getHumidity();
            }

            if (!dawnStatus.isEmpty()) {
                tempDawn /= dawnStatus.size();
                moistureDawn /= dawnStatus.size();
                humiDawn /= dawnStatus.size();
            }

            Graph graph = graphRepository.findGraphByUserPlantAndGraphDate(userPlant, LocalDate.now());

            graph.setTempDay(tempDawn);
            graph.setHumiDay(humiDawn);
            graph.setMoistureDay(moistureDawn);

            graphRepository.save(graph);
        }
    }

    // 오전 그래프 정보 업데이트(그래프 평균 data)
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    public void updateMorningGraph() {

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));

        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);

        List<UserPlant> userPlantList = userPlantRepository.findAll();

        for (UserPlant userPlant : userPlantList)
        {
            List<Status> morningStatus = statusRepository.findAllByUserPlantAndCreateTimeBetweenAndTemperatureNotAndHumidityNotAndMoistureNot(userPlant, startTime, endTime, 0.0, 0.0, 0.0);
            Double tempMorning = 0D, moistureMorning = 0D, humiMorning = 0D;

            // 유저 별로 평균 내기
            for (Status status : morningStatus) {
                tempMorning += status.getTemperature();
                moistureMorning += status.getMoisture();
                humiMorning += status.getHumidity();
            }

            if (!morningStatus.isEmpty()) {
                tempMorning /= morningStatus.size();
                moistureMorning /= morningStatus.size();
                humiMorning /= morningStatus.size();
            }

            Graph graph = graphRepository.findGraphByUserPlantAndGraphDate(userPlant, LocalDate.now());

            graph.setTempDay(tempMorning);
            graph.setHumiDay(humiMorning);
            graph.setMoistureDay(moistureMorning);

            graphRepository.save(graph);
        }
    }

    // 낮 그래프 정보 업데이트(그래프 평균 data)
    @Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
    public void updateDayGraph() {

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0));

        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);

        List<UserPlant> userPlantList = userPlantRepository.findAll();

        for (UserPlant userPlant : userPlantList)
        {
            List<Status> dayStatus = statusRepository.findAllByUserPlantAndCreateTimeBetweenAndTemperatureNotAndHumidityNotAndMoistureNot(userPlant, startTime, endTime, 0.0, 0.0, 0.0);
            Double tempDay = 0D, moistureDay = 0D, humiDay = 0D;

            // 유저 별로 평균 내기
            for (Status status : dayStatus) {
                tempDay += status.getTemperature();
                moistureDay += status.getMoisture();
                humiDay += status.getHumidity();
            }

            if (!dayStatus.isEmpty()) {
                tempDay /= dayStatus.size();
                moistureDay /= dayStatus.size();
                humiDay /= dayStatus.size();
            }

            Graph graph = graphRepository.findGraphByUserPlantAndGraphDate(userPlant, LocalDate.now());

            graph.setTempDay(tempDay);
            graph.setHumiDay(humiDay);
            graph.setMoistureDay(moistureDay);

            graphRepository.save(graph);
        }
    }

    // 밤 그래프 정보 업데이트(그래프 평균 data)
    @Scheduled(cron = "0 59 23 * * *", zone = "Asia/Seoul")
    public void updateNightGraph() {

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));

        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);

        List<UserPlant> userPlantList = userPlantRepository.findAll();

        for (UserPlant userPlant : userPlantList)
        {
            List<Status> nightStatus = statusRepository.findAllByUserPlantAndCreateTimeBetweenAndTemperatureNotAndHumidityNotAndMoistureNot(userPlant, startTime, endTime, 0.0, 0.0, 0.0);
            Double tempNight = 0D, moistureNight = 0D, humiNight = 0D;

            // 유저 별로 평균 내기
            for (Status status : nightStatus) {
                tempNight += status.getTemperature();
                moistureNight += status.getMoisture();
                humiNight += status.getHumidity();
            }

            if (!nightStatus.isEmpty()) {
                tempNight /= nightStatus.size();
                moistureNight /= nightStatus.size();
                humiNight /= nightStatus.size();
            }

            Graph graph = graphRepository.findGraphByUserPlantAndGraphDate(userPlant, LocalDate.now());

            graph.setTempNight(tempNight);
            graph.setHumiNight(humiNight);
            graph.setMoistureNight(moistureNight);

            graphRepository.save(graph);
        }
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
