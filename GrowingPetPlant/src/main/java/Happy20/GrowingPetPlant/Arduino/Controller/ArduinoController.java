package Happy20.GrowingPetPlant.Arduino.Controller;

import Happy20.GrowingPetPlant.Arduino.DTO.PostWateringReq;
import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import Happy20.GrowingPetPlant.Status.Service.StatusService;
import Happy20.GrowingPetPlant.Status.DTO.PostStatusReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        LocalDate postWateringDate = postWateringReq.getWateringDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate recentWateringDate = arduinoService.recentPlantWatering(postWateringReq.getPlantNumber());

        if (recentWateringDate.equals(postWateringDate))
            return "오늘 이미 물을 주셨습니다. 물을 주시겠습니까?";
        else
            return "물을 주시겠습니까?";
    }

    // 물 주기 api
    @PostMapping("/watering")
    public String Watering(@RequestBody PostWateringReq postWateringReq) {
        if (arduinoService.wateringPlant(postWateringReq)) {
            arduinoService.createStatus(postWateringReq);
            return "Watering Plant";
        }
        else
            return "Error";
    }

    // 조명 on api
    @PostMapping("/lightOn")
    public String LightOn() {
        if (arduinoService.lightingOnPlant())
            return "Lighting On Plant";
        else
            return "Error";
    }

    // 조명 off api
    @PostMapping("/lightOff")
    public String LightOff() {
        if (arduinoService.lightingOffPlant())
            return "Lighting Off Plant";
        else
            return "Error";
    }

    // 팬 on api
    @PostMapping("/fanOn")
    public String FanOn() {
        if (arduinoService.fanOnPlant())
            return "Fan On Plant";
        else
            return "Error";
    }

    // 팬 off api
    @PostMapping("/fanOff")
    public String FanOff() {
        if (arduinoService.fanOffPlant())
            return "Fan Off Plant";
        else
            return "Error";
    }
}
