package Happy20.GrowingPetPlant.UserPlant.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchChangeUserPlantNameReq {
    Long userPlantNumber;

    String userPlantName;

    @Builder
    public PatchChangeUserPlantNameReq(Long userPlantNumber, String userPlantName)
    {
        this.userPlantNumber = userPlantNumber;
        this.userPlantName = userPlantName;
    }
}
