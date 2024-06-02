package Happy20.GrowingPetPlant.Graph.Controller;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.GraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("graph")
public class GraphController {
    private final GraphService graphService;

    public GraphController(GraphService graphService) { this.graphService = graphService; }

    // 그래프 정보 생성(다음날로 넘어가는 정각) api
    // 모든 값 0으로 초기화
    @PostMapping("/create")
    public ResponseEntity<String> createGraph(@RequestParam("date") LocalDate date) {
        graphService.createGraph(date);
        return ResponseEntity.status(HttpStatus.OK).body("그래프 정보를 생성했습니다.");
    }

    // 그래프 정보 수정(6시간 단위) api
    @PatchMapping("/update")
    public ResponseEntity<String> modifyGraph(@RequestParam("date") LocalDate date) {
        if (graphService.updateGraph(date))
            return ResponseEntity.status(HttpStatus.OK).body("그래프 정보를 수정했습니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body("그래프 정보 수정에 실패했습니다.");

    }

    // 그래프 디스플레이 api
    @GetMapping("/display")
    public Graph displayGraph(@RequestParam("date") LocalDate date) {
        return (graphService.getGraphInfo(date));
    }
}
