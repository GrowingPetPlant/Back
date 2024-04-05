package Happy20.GrowingPetPlant.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PutLoginReq {
    private String id;

    private String password;

    @Builder
    public PutLoginReq(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
