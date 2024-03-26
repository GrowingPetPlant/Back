package Happy20.GrowingPetPlant.user.service.port;

import Happy20.GrowingPetPlant.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(String id);
    User findById(String id); // 아이디로 사용자 조회
}
