package Happy20.GrowingPetPlant.user.controller;

import Happy20.GrowingPetPlant.user.domain.User;
import Happy20.GrowingPetPlant.user.dto.PostSignupReq;
import Happy20.GrowingPetPlant.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 api
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody PostSignupReq postSignupReq) {

        User newUser = User.builder()
                .id(postSignupReq.getId())
                .password(postSignupReq.getPassword())
                .user_name(postSignupReq.getUser_name())
                .phone_number(postSignupReq.getPhone_number())
                .build();

        if (userService.signupUser(newUser))
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패했습니다.");
    }

    // 회원가입 - 아이디 중복 검사
    @GetMapping("/idCheck")
    public ResponseEntity<String> signup(@RequestParam("id") String id) {
        if (userService.idCheck(id))
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용할 수 없는 아이디입니다.");
    }

    // 로그인
    @GetMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password){
        if(userService.validationLogin(id, password)){
            return "로그인 성공";
        }
        return "로그인 실패";
    }
}