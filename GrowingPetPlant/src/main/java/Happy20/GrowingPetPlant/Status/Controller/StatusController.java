package Happy20.GrowingPetPlant.Status.Controller;

import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import Happy20.GrowingPetPlant.Status.Service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;
    private final ArduinoService arduinoService;

    // 식물 최근 온도 확인 api
    @GetMapping("/temp")
    public ResponseEntity<Double> GetRecentTemp(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return ResponseEntity.status(HttpStatus.OK).body(statusService.recentPlantTemp(plantNumber));
    }

    // 식물 최근 습도 확인 api
    @GetMapping("/moisture")
    public ResponseEntity<Double> GetMoisture(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return ResponseEntity.status(HttpStatus.OK).body(statusService.recentPlantMoisture(plantNumber));
    }

    // 식물 최근 토양 습도 확인 api
    @GetMapping("/humi")
    public ResponseEntity<Double> GetHumidity(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return ResponseEntity.status(HttpStatus.OK).body(statusService.recentPlantHumidity(plantNumber));
    }

    // 식물 토양 습도 확인 api
    @GetMapping("/name")
    public ResponseEntity<String> GetName(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return ResponseEntity.status(HttpStatus.OK).body(statusService.getPlantName(plantNumber));
    }

    // 물 준 날짜 리스트 리턴 api
    @GetMapping("/wateringdate")
    public ResponseEntity<List<LocalDate>> getWateringDates(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        List list = new ArrayList();
        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return ResponseEntity.status(HttpStatus.OK).body(statusService.getWateringDate(plantNumber));
    }


    //자란 일수 반환
    @GetMapping("/days")
    public ResponseEntity<Integer> getGrowingDays(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        int days = statusService.getGrowingDays(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(days);
    }

    //조명상태가져오기
    @GetMapping("/isLighted")
    public ResponseEntity<Boolean> getLighting(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        boolean lighted = arduinoService.getLightingStatus(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(lighted);
    }

    //팬상태가져오기
    @GetMapping("/isFanned")
    public ResponseEntity<Boolean> getFanned(@RequestParam("plantNumber") Long plantNumber, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        boolean fanned = arduinoService.getFanningStatus(plantNumber);
        return ResponseEntity.status(HttpStatus.OK).body(fanned);
    }

}
