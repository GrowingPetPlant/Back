package Happy20.GrowingPetPlant.user.controller;

import Happy20.GrowingPetPlant.user.domain.User;
import Happy20.GrowingPetPlant.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password){
        if(userService.validationLogin(id, password)){
            return "로그인 성공";
        }
        return "로그인 실패";
    }

}