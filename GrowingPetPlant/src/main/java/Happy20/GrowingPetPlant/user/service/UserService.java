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
    public boolean signupUser(User newUser) {
        if (userRepository.existsById(newUser.getId()))
            return (false);
        userRepository.save(newUser);
        return (true);
    }

    @Transactional
    public boolean idCheck(String id) {
        if (userRepository.existsById(id))
            return (false);
        return (true);
    }

    @Transactional
    public boolean validationLogin(String id, String password){
        User user = userRepository.findById(id);
        if(user != null){
            return (user.getPassword().equals(password));
        }
        return (false);
    }
}
