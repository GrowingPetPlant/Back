package Happy20.GrowingPetPlant.Arduino.Controller;

import Happy20.GrowingPetPlant.Arduino.DTO.PostWateringReq;
import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("arduino")
public class ArduinoController {
    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    // 물 주기 버튼 클릭 api
    @PostMapping("/putwater")
    public String putWatering(@RequestBody PostWateringReq postWateringReq) {
        LocalDate postWateringDate = postWateringReq.getWateringDate();
        LocalDate recentWateringDate = arduinoService.recentPlantWatering(postWateringReq.getPlantNumber());

        if (recentWateringDate.equals(postWateringDate))
            return "오늘 이미 물을 주셨습니다.\n물을 주시겠습니까?";
        else
            return "물을 주시겠습니까?";
    }

    // 물 주기 api
    @PostMapping("/watering")
    public String Watering(@RequestBody PostWateringReq postWateringReq) {
        if (arduinoService.wateringPlant(postWateringReq)) {
            return "Watering Plant";
        }
        else
            return "Error";
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
