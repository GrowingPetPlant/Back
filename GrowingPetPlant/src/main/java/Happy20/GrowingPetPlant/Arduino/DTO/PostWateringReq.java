package Happy20.GrowingPetPlant.Arduino.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class PostWateringReq {
    private Long plantNumber;

    private Date wateringDate;

    @Builder
    public PostWateringReq(Long plantNumber, Date wateringDate) {
        this.plantNumber = plantNumber;
        this.wateringDate = wateringDate;
    }
}
