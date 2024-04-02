package Happy20.GrowingPetPlant.User.Service;

import Happy20.GrowingPetPlant.User.DTO.PatchUpdateMyPageReq;
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
    public boolean createUser(PostSignupReq postSignupReq) {

        User newUser = User.builder()
                .id(postSignupReq.getId())
                .password(postSignupReq.getPassword())
                .userName(postSignupReq.getUserName())
                .phoneNumber(postSignupReq.getPhoneNumber())
                .build();
        System.out.println(newUser.getId() + "\n" + newUser.getPassword() + "\n" + newUser.getUserName() + "\n" + newUser.getPhoneNumber());
        if (userRepository.existsById(newUser.getId()))
            return (false);

        User saveUser = userRepository.save(newUser);

        UserPlant newUserPlant = UserPlant.builder()
                .plantName(postSignupReq.getPlantName())
                .plantType(postSignupReq.getPlantType())
                .userNumber(saveUser.getUserNumber())
                .build();

        userPlantRepository.save(newUserPlant);
        return (true);
    }

    @Transactional
    public boolean validateId(String id) {
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

    @Transactional
    public boolean checkUserNumberEquality(Long prevUserNumber, Long nowUserNumber) {
        if (prevUserNumber.equals(nowUserNumber))
            return (true);
        else
            return (false);
    }

    @Transactional
    public boolean validateUpdateMyPage(PatchUpdateMyPageReq patchUpdateMyPageReq) {
        User user = userRepository.findById(patchUpdateMyPageReq.getId());
        System.out.println(user);
        if (user == null)
            return (false);
        UserPlant userPlant = userPlantRepository.findByUserNumber(user.getUserNumber());
        if (userPlant == null)
            return (false);
        System.out.println(userPlant);

        if (checkUserNumberEquality(user.getUserNumber(), patchUpdateMyPageReq.getUserNumber())) {
            if (patchUpdateMyPageReq.getPassword() != null) {
                user.setPassword(patchUpdateMyPageReq.getPassword());
            }
            if (patchUpdateMyPageReq.getUserName() != null) {
                user.setUserName(patchUpdateMyPageReq.getUserName());
            }
            if (patchUpdateMyPageReq.getPhoneNumber() != null) {
                user.setPhoneNumber(patchUpdateMyPageReq.getPhoneNumber());
            }
            if (patchUpdateMyPageReq.getPlantType() != null) {
                userPlant.setPlantType(patchUpdateMyPageReq.getPlantType());
            }
            if (patchUpdateMyPageReq.getPlantName() != null) {
                userPlant.setPlantName(patchUpdateMyPageReq.getPlantName());
            }
            return (true);
        }
        else
            return (false);
    }
}
