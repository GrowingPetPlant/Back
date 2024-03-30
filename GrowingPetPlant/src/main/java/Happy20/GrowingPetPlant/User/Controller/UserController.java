package Happy20.GrowingPetPlant.User.Controller;

import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Service.UserService;
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

        if (userService.createUser(postSignupReq))
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패했습니다.");
    }

    // 회원가입 - 아이디 중복 검사 api
    @GetMapping("/idCheck")
    public ResponseEntity<String> idCheck(@RequestParam("id") String id) {
        if (userService.validateId(id))
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용할 수 없는 아이디입니다.");
    }

    // 로그인 api
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody GetLoginReq getLoginReq) {
        if (userService.validationLogin(getLoginReq)) {
            return ResponseEntity.status(HttpStatus.OK).body("로그인에 성공했습니다.");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인에 실패했습니다.");
    }

    // 마이페이지 수정 api
    @PatchMapping("/mypage")
    public ResponseEntity<String> updateMyPage(@RequestBody PatchUpdateMyPageReq patchUpdateMyPageReq) {
        if (userService.validateUpdateMyPage(patchUpdateMyPageReq)) {
            return ResponseEntity.status(HttpStatus.OK).body("마이페이지가 수정됐습니다.");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마이페이지를 수정할 수 없습니다.");
    }

    // 아이디 찾기 api
    @GetMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody GetFindUserIdReq getFindUserIdReq) {
        String userId = userService.matchUserId(getFindUserIdReq);
        if (userId != null)
            return ResponseEntity.status(HttpStatus.OK).body(getFindUserIdReq.getUserName() +
                    "사용자의 아이디는 [" + userId + "] 입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 사용자입니다.");
    }

    // 비밀번호 찾기 api
    @GetMapping("/findPwd")
    public ResponseEntity<String> findPwd(@RequestBody GetFindUserPwdReq getFindUserPwdReq) {
        String userPwd = userService.matchUserPwd(getFindUserPwdReq);
        if (userPwd != null)
            return ResponseEntity.status(HttpStatus.OK).body(getFindUserPwdReq.getUserName() +
                    "사용자의 비밀번호는 [" + userPwd + "] 입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 사용자입니다.");
    }
}