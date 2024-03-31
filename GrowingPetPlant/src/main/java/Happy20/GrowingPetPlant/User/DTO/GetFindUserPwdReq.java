package Happy20.GrowingPetPlant.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetFindUserPwdReq {

    private String id;

    private String userName;


    @Builder
    public GetFindUserPwdReq(String userName, String id, String phoneNumber) {
        this.userName = userName;
        this.id = id;
    }
}
