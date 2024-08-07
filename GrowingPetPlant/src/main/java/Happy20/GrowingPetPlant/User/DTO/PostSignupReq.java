package Happy20.GrowingPetPlant.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSignupReq {

    private Long userNumber;

    private String id;

    private String password;

    private String userName;

    private String phoneNumber;

    private String userPlantName;

    private String plantType;

    @Builder
    public PostSignupReq(String id, String password, String userName, String phoneNumber, String userPlantName, String plantType) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userPlantName = userPlantName;
        this.plantType = plantType;
    }
}
