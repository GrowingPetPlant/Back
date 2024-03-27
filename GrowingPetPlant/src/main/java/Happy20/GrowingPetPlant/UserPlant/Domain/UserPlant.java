package Happy20.GrowingPetPlant.UserPlant.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_PLANT")
@NoArgsConstructor
public class UserPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plant_number;

    private String plant_name;

    private String plant_type;

    private Long user_number;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public UserPlant(String plant_name, String plant_type, Long user_number) {
        this.plant_name = plant_name;
        this.plant_type = plant_type;
        this.user_number = user_number;
    }
}
