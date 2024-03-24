package Happy20.GrowingPetPlant.user.service.port;

import Happy20.GrowingPetPlant.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    void delete(User user);

}
