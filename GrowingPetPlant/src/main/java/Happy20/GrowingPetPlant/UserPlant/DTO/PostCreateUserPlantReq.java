package Happy20.GrowingPetPlant.UserPlant.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreateUserPlantReq {
    private String userPlantName;

    private String plantType;

    @Builder
    public PostCreateUserPlantReq(String userPlantName, String plantType) {
        this.userPlantName = userPlantName;
        this.plantType = plantType;
    }
}
