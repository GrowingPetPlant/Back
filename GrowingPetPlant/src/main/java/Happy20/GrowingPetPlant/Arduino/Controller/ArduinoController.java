package Happy20.GrowingPetPlant.Arduino.Controller;

import Happy20.GrowingPetPlant.Arduino.Service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("arduino")
public class ArduinoController {
    private final ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    // 물 주기 api
    @PostMapping("/watering")
    public String Watering() {
        if (arduinoService.wateringPlant())
            return "Watering Plant";
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
