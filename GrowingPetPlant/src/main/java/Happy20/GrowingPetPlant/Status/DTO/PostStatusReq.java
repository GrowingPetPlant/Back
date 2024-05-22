package Happy20.GrowingPetPlant.Status.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostStatusReq {
    private Long StatusNumber;

    private Long plantNumber;

    private Long moisture;

    private Long temperature;

    private Long humidity;

    private Timestamp light;

    private Date growingDate;

    private Date wateringDate;

    @Builder
    public PostStatusReq(Long plantNumber, Long moisture, Long temperature, Long humidity, Timestamp light, Date growingDate, Date wateringDate) {
        this.plantNumber = plantNumber;
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.growingDate = growingDate;
        this.wateringDate = wateringDate;
    }

}
