package Happy20.GrowingPetPlant.User.Service;

import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Port.UserPlantRepository;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


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
//        System.out.println(newUser.getId() + "\n" + newUser.getPassword() + "\n" + newUser.getUserName() + "\n" + newUser.getPhoneNumber());
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
    public boolean validationLogin(PostLoginReq putLoginReq){
        User user = userRepository.findById(putLoginReq.getId());
        if(user != null)
            return (user.getPassword().equals(putLoginReq.getPassword()));
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
        if (user == null)
            return (false);

        UserPlant userPlant = userPlantRepository.findByUserNumber(user.getUserNumber());
        if (userPlant == null)
            return (false);

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

    public String matchUserId(GetFindUserIdReq getFindUserIdReq) {

        User user = userRepository.findByUserName(getFindUserIdReq.getUserName());

        if (user == null)
            return (null);

        if (getFindUserIdReq.getPhoneNumber().equals(user.getPhoneNumber()))
            return (user.getId());
        else
            return (null);
    }

    public String matchUserPwd(GetFindUserPwdReq getFindUserPwdReq) {

        User user = userRepository.findById(getFindUserPwdReq.getId());

        if (user == null)
            return (null);

        if (getFindUserPwdReq.getUserName().equals(user.getUserName()))
            return (user.getPassword());
        else
            return (null);
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(String id){
        User user = userRepository.findById(id);

        if(user != null){
            userRepository.delete(user);
        }else{
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }
    }
}
