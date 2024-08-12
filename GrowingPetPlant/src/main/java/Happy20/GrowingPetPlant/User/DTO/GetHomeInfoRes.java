package Happy20.GrowingPetPlant.User.DTO;

import Happy20.GrowingPetPlant.Status.Domain.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetHomeInfoRes {

    // 유저 번호, 유저 식물 번호, 유저 식물 이름, 현재 습도, 기온, 토양 습도
    String message;
    Long userNumber;
    Long userPlantNumber;
    String userPlantName;
    Double moisture;
    Double humidity;
    Double temperature;

    @Builder(builderMethodName = "invalidHomeInfoBuilder")
    public GetHomeInfoRes(String message) {
        this.message = message;
    }

    @Builder(builderMethodName = "HomeInfoBuilder")
    public GetHomeInfoRes(String message, Long userNumber, Long userPlantNumber, String userPlantName,
                          Double moisture, Double humidity, Double temperature) {
        this.message = message;
        this.userNumber = userNumber;
        this.userPlantNumber = userPlantNumber;
        this.userPlantName = userPlantName;
        this.moisture = moisture;
        this.humidity = humidity;
        this.temperature = temperature;
    }
}
