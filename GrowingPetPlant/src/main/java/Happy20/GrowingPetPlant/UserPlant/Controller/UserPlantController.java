package Happy20.GrowingPetPlant.UserPlant.Controller;

import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import Happy20.GrowingPetPlant.UserPlant.DTO.PostCreateUserPlantReq;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import Happy20.GrowingPetPlant.UserPlant.Service.UserPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userplant")
@RequiredArgsConstructor
public class UserPlantController {
    private final UserPlantService userPlantService;
    private final UserPlantRepository userPlantRepository;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> createUserPlant(@RequestBody PostCreateUserPlantReq postCreateUserPlantReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userRepository.findById(principal.getName()); // 유저 조회

        if (userPlantRepository.existsByUserAndUserPlantName(user, postCreateUserPlantReq.getUserPlantName())) // 유저-식물 이름 중복 예외 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 식물 이름입니다.\n");

        userPlantService.createUserPlant(user, postCreateUserPlantReq.getUserPlantName(), postCreateUserPlantReq.getPlantType()); // 유저-식물 정보 생성

        return ResponseEntity.status(HttpStatus.CONFLICT).body("식물 정보를 생성했습니다.\n");
    }

    @GetMapping("/findAllPlant")
    public List<Long> findAllPlant() {
        return userPlantService.findAllPlantNumber();
    }

    @DeleteMapping("/delete/{userPlantNumber}")
    public ResponseEntity<String> deleteUserPlant(@PathVariable("userPlantNumber") Long userPlantNumber, Authentication principal)
    {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 유저 정보입니다.\n");

        if (userPlantService.deletePlant(userPlantNumber))
            return ResponseEntity.status(HttpStatus.OK).body("유저 식물 정보가 삭제되었습니다.\n");

        return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하지 않는 식물 정보입니다.\n");
    }

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
}
