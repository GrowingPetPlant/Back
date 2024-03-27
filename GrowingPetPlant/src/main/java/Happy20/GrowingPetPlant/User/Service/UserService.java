package Happy20.GrowingPetPlant.User.Service;

import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.User.DTO.GetLoginReq;
import Happy20.GrowingPetPlant.User.DTO.PostSignupReq;
import Happy20.GrowingPetPlant.UserPlant.Port.UserPlantRepository;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPlantRepository userPlantRepository;

    @Transactional
    public boolean signupUser(PostSignupReq postSignupReq) {

        User newUser = User.builder()
                .id(postSignupReq.getId())
                .password(postSignupReq.getPassword())
                .user_name(postSignupReq.getUserName())
                .phone_number(postSignupReq.getPhoneNumber())
                .build();

        if (userRepository.existsById(newUser.getId()))
            return (false);

        User saveUser = userRepository.save(newUser);

        UserPlant newUserPlant = UserPlant.builder()
                .plant_name(postSignupReq.getPlantName())
                .plant_type(postSignupReq.getPlantType())
                .user_number(saveUser.getUserNumber())
                .build();

        userPlantRepository.save(newUserPlant);
        return (true);
    }

    @Transactional
    public boolean idCheck(String id) {
        if (userRepository.existsById(id))
            return (false);
        return (true);
    }

    @Transactional
    public boolean validationLogin(GetLoginReq getLoginReq){
        User user = userRepository.findById(getLoginReq.getId());
        if(user != null)
            return (user.getPassword().equals(getLoginReq.getPassword()));
        else
            return (false);
    }
}
