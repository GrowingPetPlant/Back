package Happy20.GrowingPetPlant.User.Service;

import Happy20.GrowingPetPlant.Plant.Domain.Plant;
import Happy20.GrowingPetPlant.Plant.PlantRepository;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final UserPlantRepository userPlantRepository;
    private final StatusRepository statusRepository;

    // 회원 생성
    @Transactional
    public boolean createUser(PostSignupReq postSignupReq) {

        if (userRepository.existsById(postSignupReq.getId()))
            return (false);

        if (userRepository.existsByUserName(postSignupReq.getUserName()))
            return (false);

        User newUser = User.builder()
                .id(postSignupReq.getId())
                .password(postSignupReq.getPassword())
                .userName(postSignupReq.getUserName())
                .phoneNumber(postSignupReq.getPhoneNumber())
                .build();

        User saveUser = userRepository.save(newUser);

        Plant plant = plantRepository.findPlantByPlantName(postSignupReq.getPlantName());

        UserPlant newUserPlant = UserPlant.builder()
                .userPlantName(postSignupReq.getPlantName())
                .userPlantType(postSignupReq.getPlantType())
                .main(true)
                .wateringDate(null)
                .user(saveUser)
                .plant(plant)
                .build();

        userPlantRepository.save(newUserPlant);

        Status newStatus = Status.builder()
                .moisture(0D)
                .temperature(0D)
                .humidity(0D)
                .light(Boolean.FALSE)
                .fan(Boolean.FALSE)
                .watering(Boolean.FALSE)
                .userPlant(newUserPlant)
                .build();

        statusRepository.save(newStatus);

        return (true);
    }

    // 유효한 유저인지 확인
    @Transactional
    public User validateUser(Long userNumber) { return userRepository.findByUserNumber(userNumber); }

    // 유효한 아이디인지 확인
    @Transactional
    public boolean validateId(String id) { return !userRepository.existsById(id); }

    // 유효한 닉네임인지 확인
    @Transactional
    public boolean validateName(String name) { return !userRepository.existsByUserName(name); }

    // 로그인 확인
    @Transactional
    public Long validationLogin(PostLoginReq postLoginReq){
        User user = userRepository.findById(postLoginReq.getId());
        if(user != null && user.getPassword().equals(postLoginReq.getPassword()))
            return (user.getUserNumber());
        else
            return (null);
    }

    // 유저 번호 동일한지 확인
    @Transactional
    public boolean checkUserNumberEquality(Long prevUserNumber, Long nowUserNumber) {
        if (prevUserNumber.equals(nowUserNumber))
            return (true);
        else
            return (false);
    }

    // 마이페이지 업데이트
    @Transactional
    public boolean validateUpdateMyPage(PatchUpdateMyPageReq patchUpdateMyPageReq) {

        User user = userRepository.findById(patchUpdateMyPageReq.getId());
        if (user == null)
            return (false);

        List<UserPlant> userPlant = userPlantRepository.findAllByUser(user);
        if (userPlant == null)
            return (false);

        // 아이디는 변경 불가 -> 아이디 바꾸지 않은 경우
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
            // 식물 정보 따로 수정하는 페이지 필요 !!
//            if (patchUpdateMyPageReq.getPlantType() != null) {
//                userPlant.setUserPlantType(patchUpdateMyPageReq.getPlantType());
//            }
//            if (patchUpdateMyPageReq.getPlantName() != null) {
//                userPlant.setUserPlantName(patchUpdateMyPageReq.getPlantName());
//            }
            return (true);
        }
        else
            return (false);
    }

    // 유저 아이디 찾기
    public String matchUserId(GetFindUserIdReq getFindUserIdReq) {

        User user = userRepository.findByUserName(getFindUserIdReq.getUserName());

        if (user == null)
            return (null);

        if (getFindUserIdReq.getPhoneNumber().equals(user.getPhoneNumber()))
            return (user.getId());
        else
            return (null);
    }

    // 유저 비밀번호 찾기
    public String matchUserPwd(GetFindUserPwdReq getFindUserPwdReq) {

        User user = userRepository.findById(getFindUserPwdReq.getId());

        if (user == null)
            return (null);

        if (getFindUserPwdReq.getUserName().equals(user.getUserName()))
            return (user.getPassword());
        else
            return (null);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(String id){
        User user = userRepository.findById(id);
        List<UserPlant> userPlantList = userPlantRepository.findAllByUser(user);

        if(user != null) {
            userPlantRepository.deleteAll(userPlantList);
            userRepository.delete(user);
        } else{
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }
    }
}
