package Happy20.GrowingPetPlant.UserPlant.Service;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.Port.GraphRepository;
import Happy20.GrowingPetPlant.Plant.Domain.Plant;
import Happy20.GrowingPetPlant.Plant.PlantRepository;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.User.Domain.User;
import Happy20.GrowingPetPlant.UserPlant.DTO.PostCreateUserPlantReq;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPlantService {

    private final UserPlantRepository userPlantRepository;
    private final PlantRepository plantRepository;
    private final StatusRepository statusRepository;
    private final GraphRepository graphRepository;

    @Transactional
    public void createUserPlant(User user, String userPlantName, String plantType) {
        Plant plant = plantRepository.findPlantByPlantType(plantType); // 식물 종 선택

        UserPlant newUserPlant = UserPlant.builder()
                .userPlantName(userPlantName)
                .wateringDate(null)
                .user(user)
                .plant(plant)
                .build();

        userPlantRepository.save(newUserPlant); // 식물 정보 생성

        Status newStatus = Status.statusByUserPlantBuilder()
                .userPlant(newUserPlant)
                .build();

        statusRepository.save(newStatus); // 상태 정보 생성

        Graph newGraph = Graph.builder()
                .userPlant(newUserPlant)
                .graphDate(LocalDate.now())
                .build();

        graphRepository.save(newGraph); // 그래프 정보 생성
    }

    @Transactional
    public List<Long> findAllPlantNumber() {
        return userPlantRepository.findAllByuserPlant();
    }


//    @Transactional
//    public UserPlant validateUserPlant(Long userNumber) {
//        return userPlantRepository.findByUserNumber(userNumber);
//    }

}
