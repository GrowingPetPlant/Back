package Happy20.GrowingPetPlant.user.controller;

import Happy20.GrowingPetPlant.user.domain.User;
import Happy20.GrowingPetPlant.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public User doTest() {
        return userService.makeTestUser();
    }

}
