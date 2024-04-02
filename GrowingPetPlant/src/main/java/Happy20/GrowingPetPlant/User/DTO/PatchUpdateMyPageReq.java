package Happy20.GrowingPetPlant.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchUpdateMyPageReq {
    private Long userNumber;

    private String id;

    private String password;

    private String userName;

    private String phoneNumber;

    private String plantType;

    private String plantName;

    @Builder
    public PatchUpdateMyPageReq(Long userNumber, String id, String password, String userName, String phoneNumber, String plantType, String plantName) {
        this.userNumber = userNumber;
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.plantType = plantType;
        this.plantName = plantName;
    }
}
