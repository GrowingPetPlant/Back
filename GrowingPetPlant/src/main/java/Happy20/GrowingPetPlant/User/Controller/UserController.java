package Happy20.GrowingPetPlant.User.Controller;

import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Service.UserService;
import Happy20.GrowingPetPlant.User.Domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public String signup(@Valid @RequestBody PostSignupReq postSignupReq) {
        if (userService.createUser(postSignupReq))
            return "회원가입에 성공했습니다.";
        else
            return "회원가입에 실패했습니다.";
    }

    // 회원가입 - 아이디 중복 검사 api
    @PostMapping("/idCheck")
    public ResponseEntity<String> idCheck(@RequestParam("id") String id) {
        if (userService.validateId(id))
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용할 수 없는 아이디입니다.");
    }

    // 로그인 api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PostLoginReq putLoginReq) {
        if (userService.validationLogin(putLoginReq)) {
            return ResponseEntity.status(HttpStatus.OK).body(putLoginReq.getId());
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
    @PostMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody GetFindUserIdReq getFindUserIdReq) {
        String userId = userService.matchUserId(getFindUserIdReq);
        if (userId != null)
            return ResponseEntity.status(HttpStatus.OK).body(getFindUserIdReq.getUserName() +
                    " 사용자의 아이디는\n[" + userId + "] 입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 사용자입니다.");
    }

    // 비밀번호 찾기 api
    @PostMapping("/findPwd")
    public ResponseEntity<String> findPwd(@RequestBody GetFindUserPwdReq getFindUserPwdReq) {
        String userPwd = userService.matchUserPwd(getFindUserPwdReq);
        if (userPwd != null)
            return ResponseEntity.status(HttpStatus.OK).body(getFindUserPwdReq.getUserName() +
                    "사용자의 비밀번호는\n [" + userPwd + "] 입니다.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 사용자입니다.");
    }

    //로그아웃 api
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        //세션 무효화
        request.getSession().invalidate();

        //클라이언트에게 로그아웃 성공 메시지와 HTTP 상태 코드 반환
        return new ResponseEntity<>("로그아웃 되었습니다", HttpStatus.OK);
    }

    //탈퇴하기
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return id+ " 사용자 탈퇴가 완료되었습니다.";
    }
}