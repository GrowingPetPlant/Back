package Happy20.GrowingPetPlant.Graph.Controller;

import Happy20.GrowingPetPlant.Graph.Domain.Graph;
import Happy20.GrowingPetPlant.Graph.Service.GraphService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("graph")
@RequiredArgsConstructor
public class GraphController {
    private final GraphService graphService;

    // 그래프 디스플레이 api
    @GetMapping("/display")
    public ResponseEntity<Graph> displayGraph(@RequestParam("userPlantNumber") Long userPlantNumber, @RequestParam("date") LocalDate date, Authentication principal) {

        // 로그인 정보 확인
        if (principal == null)
            return (ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        return (ResponseEntity.status(HttpStatus.OK).body(graphService.getGraphInfo(userPlantNumber, date)));
    }
}
