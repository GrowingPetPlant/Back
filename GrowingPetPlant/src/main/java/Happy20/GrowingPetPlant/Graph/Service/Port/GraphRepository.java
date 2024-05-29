package Happy20.GrowingPetPlant.Graph.Service.Port;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface GraphRepository extends JpaRepository<Graph, Long> {
    Graph findByDate(LocalDate date); // 그래프 날짜로 그래프 조회
}
