package Happy20.GrowingPetPlant.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSignupReq {

    private Long user_number;

    private String id;

    private String password;

    private String user_name;

    private String phone_number;

    @Builder
    public PostSignupReq(String id, String password, String user_name, String phone_number) {
        this.id = id;
        this.password = password;
        this.user_name = user_name;
        this.phone_number = phone_number;
    }
}
