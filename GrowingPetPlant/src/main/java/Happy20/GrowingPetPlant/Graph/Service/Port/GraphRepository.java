package Happy20.GrowingPetPlant.Graph.Service.Port;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepository extends JpaRepository<Graph, Long> {
}
