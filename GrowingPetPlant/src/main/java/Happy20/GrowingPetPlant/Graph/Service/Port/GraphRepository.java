package Happy20.GrowingPetPlant.Graph.Service.Port;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GraphRepository extends JpaRepository<Graph, Long> {
    // 그래프 날짜로 그래프 조회
    Graph findGraphByGraphDate(LocalDate date);

    // 유저-식물 번호, 그래프 일자로 그래프 조회
    Graph findGraphByUserPlantAndGraphDate(UserPlant userPlant, LocalDate date);
}
