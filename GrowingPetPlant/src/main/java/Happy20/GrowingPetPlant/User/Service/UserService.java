package Happy20.GrowingPetPlant.User.Service;

import Happy20.GrowingPetPlant.JWT.JwtProvider;
import Happy20.GrowingPetPlant.JWT.TokenDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.User.DTO.*;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.User.Service.Port.UserRepository;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPlantRepository userPlantRepository;
    private final StatusRepository statusRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    private final String PREFIX_LOGOUT = "LOGOUT:";
    private final String PREFIX_LOGOUT_REFRESH = "LOGOUT_REFRESH:";

    private final Logger log = LoggerFactory.getLogger(getClass());

    // 토큰 정보 리턴
    @Transactional
    public TokenDto setTokenInHeader(User loginUser, HttpServletResponse response) {
        // 토큰 생성
        TokenDto jwtToken = jwtProvider.generateToken(loginUser);

        // 액세스 토큰 발급
        log.info("request id = {}, password = {}", loginUser.getId(), loginUser.getPassword());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        // Header에 정보 넘겨주기
        response.setHeader("TokenType", "Bearer");
        response.setHeader("AccessToken", jwtToken.getAccessToken());
        response.setHeader("RefreshToken", jwtToken.getRefreshToken());

        return jwtToken;
    }

    // 회원 정보 생성
    @Transactional
    public User createUser(PostSignupReq postSignupReq) {

        String encodePw = passwordEncoder.encode(postSignupReq.getPassword());

        User newUser = User.builder()
                .id(postSignupReq.getId())
                .password(encodePw)
                .userName(postSignupReq.getUserName())
                .phoneNumber(postSignupReq.getPhoneNumber())
                .build();

        userRepository.save(newUser);

        return newUser;
    }

    // 로그인 확인
    @Transactional
    public PostLoginRes validationLogin(User user, String password, HttpServletResponse response) {

        if (passwordEncoder.matches(password, user.getPassword())) {
            // 토큰 생성 및 헤더에 토큰 정보 추가
            TokenDto token = setTokenInHeader(user, response);
            return (new PostLoginRes("로그인에 성공했습니다.\n", token));
        }
        else
            return (new PostLoginRes("잘못된 비밀번호입니다.\n", null));
    }

    // 로그아웃
    @Transactional
    public void logoutMember(HttpServletRequest request, String id) {

        String accessToken = jwtProvider.resolveAccessToken(request);
        String refreshToken = jwtProvider.resolveRefreshToken(request);
        Date accessExpiration = jwtProvider.parseClaims(accessToken).getExpiration();
        Date refreshExpiration = jwtProvider.parseClaims(refreshToken).getExpiration();

        redisTemplate.opsForValue()
                .set(PREFIX_LOGOUT + id, accessToken, Duration.ofSeconds(accessExpiration.getTime() - new Date().getTime()));
        redisTemplate.opsForValue()
                .set(PREFIX_LOGOUT_REFRESH + id, refreshToken, Duration.ofSeconds(refreshExpiration.getTime() - new Date().getTime()));
    }

    // 홈 화면 정보 리턴
    @Transactional
    public List<GetHomeInfoRes> getHomeInfo(String id, List<GetHomeInfoRes> getHomeInfoResList) {
        User user = userRepository.findById(id);
        List<UserPlant> userPlantList = userPlantRepository.findAllByUser(user);

        for (UserPlant userPlant : userPlantList)
        {
            Status status = statusRepository.findFirstByUserPlantOrderByStatusNumberDesc(userPlant);
            getHomeInfoResList.add(new GetHomeInfoRes(user.getUserName() + " 유저 홈 화면 정보입니다.\n", user.getUserNumber(),
                    userPlant.getUserPlantNumber(), userPlant.getUserPlantName(), status.getMoisture(), status.getHumidity(), status.getTemperature()));
        }

        return (getHomeInfoResList);
    }

    // 유효한 유저인지 확인
    @Transactional
    public User validateUser(Long userNumber) { return userRepository.findByUserNumber(userNumber); }

    // 유효한 아이디인지 확인
    @Transactional
    public boolean validateId(String id) { return userRepository.existsById(id); }

    // 유효한 닉네임인지 확인
    @Transactional
    public boolean validateName(String name) { return userRepository.existsByUserName(name); }

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
