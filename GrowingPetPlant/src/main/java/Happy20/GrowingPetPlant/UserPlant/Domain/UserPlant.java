package Happy20.GrowingPetPlant.UserPlant.Domain;

import Happy20.GrowingPetPlant.Plant.Domain.Plant;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.User.Domain.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_plant")
@NoArgsConstructor
public class UserPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_plant_number")
    private Long userPlantNumber;

    @NotBlank(message = "식물 이름을 입력해주세요.")
    @Column(name = "user_plant_name")
    private String userPlantName;

    @NotBlank(message = "식물 종류를 선택해주세요.")
    @Column(name = "user_plant_type")
    private String userPlantType;

    @Column(name = "growing_date")
    private LocalDate growingDate;

    @Column(name = "watering_date")
    private LocalDate wateringDate;

    @Column(name = "main")
    private Boolean main;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_number")
    private Plant plant;

    @OneToMany(mappedBy = "userPlant", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Status> statusList = new ArrayList<>();

    @Builder
    public UserPlant(String userPlantName, String userPlantType, LocalDate wateringDate, Boolean main, User user, Plant plant) {
        this.userPlantName = userPlantName;
        this.userPlantType = userPlantType;
        this.growingDate = LocalDate.now();;
        this.main = main;
        this.wateringDate = wateringDate;
        this.user = user;
        this.plant = plant;
    }
}
