package Happy20.GrowingPetPlant.UserPlant.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "식물 이름을 입력해주세요.")
    @Column(name = "plant_name")
    private String plantName;

    @NotBlank(message = "식물 종류를 선택해주세요.")
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
