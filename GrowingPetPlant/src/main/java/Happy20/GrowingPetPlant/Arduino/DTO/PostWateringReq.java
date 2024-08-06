package Happy20.GrowingPetPlant.Arduino.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PostWateringReq {
    private Long userPlantNumber;

    private LocalDate wateringDate;

    @Builder
    public PostWateringReq(Long userPlantNumber, LocalDate wateringDate) {
        this.userPlantNumber = userPlantNumber;
        this.wateringDate = wateringDate;
    }
}
