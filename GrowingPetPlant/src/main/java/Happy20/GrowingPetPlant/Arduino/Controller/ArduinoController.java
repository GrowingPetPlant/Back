package Happy20.GrowingPetPlant.Arduino.Controller;

import Happy20.GrowingPetPlant.Arduino.DTO.PostWateringReq;
import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("arduino")
@RequiredArgsConstructor
public class ArduinoController {
    private final ArduinoService arduinoService;

    // 물 주기 버튼 클릭 api
    @PostMapping("/putwater")
    public ResponseEntity<String> putWatering(@RequestBody PostWateringReq postWateringReq, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        LocalDate postWateringDate = postWateringReq.getWateringDate();
        LocalDate recentWateringDate = arduinoService.recentPlantWatering(postWateringReq.getUserPlantNumber());

        if (recentWateringDate == null)
            return ResponseEntity.status(HttpStatus.OK).body("물을 주시겠습니까?\n");
        else if (recentWateringDate.equals(postWateringDate))
            return ResponseEntity.status(HttpStatus.OK).body("오늘 이미 물을 주셨습니다.\n물을 주시겠습니까?\n");
        else
            return ResponseEntity.status(HttpStatus.OK).body("물을 주시겠습니까?\n");
    }

    // 물 주기 api
    @PostMapping("/watering")
    public ResponseEntity<String> Watering(@RequestBody PostWateringReq postWateringReq, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        if (arduinoService.wateringPlant(postWateringReq)) {
            return ResponseEntity.status(HttpStatus.OK).body("Watering Plant\n");
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body("Error\n");
    }

    // 조명 on/off api
    @PostMapping("/lighting")
    public ResponseEntity<Boolean> Lighting(@RequestParam("plantNumber") Long plantNumber) {
        if (arduinoService.lightingPlant(plantNumber)) // 조명바꾸기
        {
            if (arduinoService.getLightingStatus(plantNumber)) //지금 켜져있으면 true
                return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
            else
                return ResponseEntity.status(HttpStatus.OK).body(Boolean.FALSE);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Boolean.FALSE);
    }

    // 팬 on/off api
    @PostMapping("/fanning")
    public ResponseEntity<Boolean> Fanning(@RequestParam("plantNumber") Long plantNumber) {
        if (arduinoService.fanningPlant(plantNumber)) {
            if (arduinoService.getFanningStatus(plantNumber))
                return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
            else
                return ResponseEntity.status(HttpStatus.OK).body(Boolean.FALSE);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Boolean.FALSE);
    }
}
