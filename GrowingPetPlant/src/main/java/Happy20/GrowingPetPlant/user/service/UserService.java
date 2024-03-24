package Happy20.GrowingPetPlant.user.service;

import Happy20.GrowingPetPlant.user.domain.User;
import Happy20.GrowingPetPlant.user.service.port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User makeTestUser() {
        User saveUser = User.builder()
                .name("테스트 유저 이름")
                .build();

        return userRepository.save(saveUser);
    }

}
