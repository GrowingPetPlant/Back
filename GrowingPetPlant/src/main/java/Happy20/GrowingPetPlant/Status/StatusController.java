package Happy20.GrowingPetPlant.Status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Happy20.GrowingPetPlant.Status.StatusService;


@RestController
@RequestMapping("status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    // 식물 온도 확인 api
    @GetMapping("/temp")
    public Long GetTemp(@RequestParam("plantNumber") Long plantNumber) {
        return statusService.getPlantTemp(plantNumber);
    }
}
