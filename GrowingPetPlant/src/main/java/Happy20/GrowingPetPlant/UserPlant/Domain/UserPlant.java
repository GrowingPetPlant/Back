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
    @Column(name = "plant_number")
    private Long plantNumber;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "plant_type")
    private String plantType;

    @Column(name = "user_number")
    private Long userNumber;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public UserPlant(String plantName, String plantType, Long userNumber) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.userNumber = userNumber;
    }
}
