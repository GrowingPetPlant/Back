package Happy20.GrowingPetPlant.user.service;

import Happy20.GrowingPetPlant.user.domain.User;
import Happy20.GrowingPetPlant.user.service.port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    //생성자 - UserRepository 타입의 객체 주입
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean validationLogin(String id, String password){
        User user = userRepository.findById(id);
        if(user != null){
            return user.getPassword().equals(password);
        }
        return false;
    }

}
