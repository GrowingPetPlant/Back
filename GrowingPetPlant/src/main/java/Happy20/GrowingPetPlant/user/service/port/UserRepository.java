package Happy20.GrowingPetPlant.user.service.port;

import Happy20.GrowingPetPlant.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
