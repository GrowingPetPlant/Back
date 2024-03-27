package Happy20.GrowingPetPlant.User.DTO;

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

    private String plant_type;

    private String plant_name;

    @Builder
    public PostSignupReq(String id, String password, String user_name, String phone_number, String plant_type, String plant_name) {
        this.id = id;
        this.password = password;
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.plant_type = plant_type;
        this.plant_name = plant_name;
    }

    public String getUserName() { return user_name; }
    public String getPhoneNumber() { return phone_number; }
    public String getPlantType() { return plant_type; }
    public String getPlantName() { return plant_name; }
}
