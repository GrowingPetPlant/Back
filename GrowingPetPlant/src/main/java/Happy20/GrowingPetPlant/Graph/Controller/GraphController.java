package Happy20.GrowingPetPlant.Graph.Controller;

import Happy20.GrowingPetPlant.Graph.Service.GraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("graph")
public class GraphController {
    private final GraphService graphService;

    public GraphController(GraphService graphService) { this.graphService = graphService; }

    // 그래프 정보 생성 api
    @PostMapping("/create")
    public ResponseEntity<String> createGraph(@RequestParam("createTime") LocalDate today) {
        graphService.createGraph(today);
        return ResponseEntity.status(HttpStatus.OK).body("그래프 정보를 생성했습니다.");
    }
}
