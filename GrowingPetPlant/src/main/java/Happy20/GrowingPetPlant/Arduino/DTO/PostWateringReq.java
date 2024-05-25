package Happy20.GrowingPetPlant.Arduino.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PostWateringReq {
    private Long plantNumber;

    private LocalDate wateringDate;

    @Builder
    public PostWateringReq(Long plantNumber, LocalDate wateringDate) {
        this.plantNumber = plantNumber;
        this.wateringDate = wateringDate;
    }
}
