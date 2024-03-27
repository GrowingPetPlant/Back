package Happy20.GrowingPetPlant.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLoginReq {
    private String id;

    private String password;

    @Builder
    public GetLoginReq(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
