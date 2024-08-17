package Happy20.GrowingPetPlant.User.Controller;

import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import Happy20.GrowingPetPlant.User.Service.UserService;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Service.UserPlantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserPlantService userPlantService;

    // 회원가입 api
    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@Valid @RequestBody PostSignupReq postSignupReq, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효성 검사 오류: " + bindingResult.getAllErrors().get(0).getDefaultMessage() + "\n");

        if (userService.validateId(postSignupReq.getId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디입니다.");

        if (userService.validateName(postSignupReq.getId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다.");

        User newUser = userService.createUser(postSignupReq); // 유저 정보 생성

        userPlantService.createUserPlant(newUser, postSignupReq.getUserPlantName(), postSignupReq.getPlantType()); // 유저-식물 및 상태, 그래프 정보 생성

        return ResponseEntity.status(HttpStatus.OK).body("회원가입에 성공했습니다.\n");
    }

    // 회원가입 - 아이디 중복 검사 api
    @GetMapping("/id-check")
    public ResponseEntity<String> idCheck(@RequestParam("id") String id) {
        if (!userService.validateId(id))
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디입니다.");
    }

    // 회원가입 - 닉네임 중복 검사 api
    @GetMapping("/name-check")
    public ResponseEntity<String> nameCheck(@RequestParam("name") String name) {
        if (!userService.validateName(name))
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다.");
    }

    // 로그인 api
    @PostMapping("/login")
    public ResponseEntity<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq, HttpServletResponse response) {

        User user = userRepository.findById(postLoginReq.getId());

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PostLoginRes("잘못된 유저 정보입니다.\n", null));
        else
            return ResponseEntity.status(HttpStatus.OK).body(userService.validationLogin(user, postLoginReq.getPassword(), response));
    }

    // 로그아웃 api
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication principal, HttpServletRequest request) {

        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 유저 정보입니다.\n");

        userService.logoutMember(request, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 처리되었습니다.\n");
    }

    // 회원 탈퇴 api
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return (ResponseEntity.status(HttpStatus.OK).body(id + " 사용자 탈퇴가 완료되었습니다.\n"));
    }

    // 홈 화면 정보 api
    @GetMapping("/home")
    public ResponseEntity<List<GetHomeInfoRes>> homeInfo(Authentication principal) {
        List<GetHomeInfoRes> homeInfoResList = new ArrayList<>();
        if (principal == null) {
            homeInfoResList.add(new GetHomeInfoRes("잘못된 유저 정보입니다.\n"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(homeInfoResList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.getHomeInfo(principal.getName(), homeInfoResList));
    }

    // 마이페이지 수정 api
    @PatchMapping("/mypage")
    public ResponseEntity<String> updateMyPage(@Valid @RequestBody PatchUpdateMyPageReq patchUpdateMyPageReq, BindingResult bindingResult, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 유저 정보입니다.\n"));

        // 유효성 검증
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효성 검사 오류: " + bindingResult.getAllErrors().get(0).getDefaultMessage() + "\n");

        return (userService.validateUpdateMyPage(patchUpdateMyPageReq, principal));
    }

    // 유저 번호 -> 유저 찾기 api
    @GetMapping("/findUser")
    public ResponseEntity<User> findUser(@RequestParam("userNumber") Long userNumber) {
        User user = userService.validateUser(userNumber);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(user);
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
}