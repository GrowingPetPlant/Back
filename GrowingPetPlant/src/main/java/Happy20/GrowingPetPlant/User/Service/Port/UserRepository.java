package Happy20.GrowingPetPlant.User.Service.Port;

import Happy20.GrowingPetPlant.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(String id); // 해당 아이디가 DB에 존재하는지 조회
    User findById(String id); // 아이디로 사용자 조회

    User findByUserName(String userName); // 유저명으로 사용자 조회
}
