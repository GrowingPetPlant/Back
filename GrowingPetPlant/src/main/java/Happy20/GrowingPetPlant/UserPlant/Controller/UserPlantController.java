package Happy20.GrowingPetPlant.UserPlant.Controller;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.UserPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("userplant")
public class UserPlantController {
    private final UserPlantService userPlantService;

    @Autowired
    public UserPlantController(UserPlantService userPlantService){this.userPlantService = userPlantService;}

//    // 유저번호 -> 식물정보찾기
//    @GetMapping("/findUserPlant")
//    public ResponseEntity<UserPlant> findUserPlantById(@RequestParam("userNumber") String userNumber) {
//        UserPlant userPlant = userPlantService.validateUserPlant(Long.valueOf(userNumber));
//
//        if(userPlant==null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        else
//            return ResponseEntity.status(HttpStatus.OK).body(userPlant);
//    }

    @GetMapping("/findAllPlant")
    public List<Long> findAllPlant() {
        return userPlantService.findAllPlantNumber();
    }
}
