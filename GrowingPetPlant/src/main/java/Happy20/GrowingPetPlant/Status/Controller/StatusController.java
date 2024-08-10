package Happy20.GrowingPetPlant.Status.Controller;

import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import Happy20.GrowingPetPlant.Status.Service.StatusService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("status")
public class StatusController {
    private final StatusService statusService;
    private final ArduinoService arduinoService;

    public StatusController(StatusService statusService, ArduinoService arduinoService) {
        this.statusService = statusService;
        this.arduinoService = arduinoService;
    }

    // 식물 상태 정각 생성(1시간) api
//    @PostMapping("/create")
//    public ResponseEntity<String> createStatus(@RequestParam("plantNumber") Long plantNumber) {
//        statusService.createStatus(plantNumber);
//        return ResponseEntity.status(HttpStatus.OK).body("상태를 생성했습니다.");
//    }

    // 식물 최근 온도 확인 api
    @GetMapping("/temp")
    public Double GetRecentTemp(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.recentPlantTemp(plantNumber);
    }

    // 식물 최근 습도 확인 api
    @GetMapping("/moisture")
    public Double GetMoisture(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.recentPlantMoisture(plantNumber);
    }

    // 식물 최근 토양 습도 확인 api
    @GetMapping("/humi")
    public Double GetHumidity(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.recentPlantHumidity(plantNumber);
    }

    // 식물 토양 습도 확인 api
    @GetMapping("/name")
    public String GetName(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.getPlantName(plantNumber);
    }

    // 물 준 날짜 리스트 리턴 api
    @GetMapping("/wateringdate")
    public List<LocalDate> getWateringDates(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.getWateringDate(plantNumber);
    }


    //자란 일수 반환
    @GetMapping("/days")
    public ResponseEntity<Integer> getGrowingDays(@RequestParam("plantNumber") Long plantNumber) {
        int days = statusService.getGrowingDays(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(days);
    }

    //조명상태가져오기
    @GetMapping("/isLighted")
    public ResponseEntity<Boolean> getLighting(@RequestParam("plantNumber") Long plantNumber) {
        boolean lighted = arduinoService.getLightingStatus(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(lighted);
    }

    //팬상태가져오기
    @GetMapping("/isFanned")
    public ResponseEntity<Boolean> getFanned(@RequestParam("plantNumber") Long plantNumber) {
        boolean fanned = arduinoService.getFanningStatus(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(fanned);
    }

}
