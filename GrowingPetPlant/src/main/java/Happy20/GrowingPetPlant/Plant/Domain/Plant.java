package Happy20.GrowingPetPlant.Plant.Domain;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "plant")
@NoArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_number")
    private Long plantNumber;

    @Column(name = "plant_type")
    private String plantType;

    @Column(name = "period")
    private Long period;

    @Builder
    public Plant(String plantType, Long period) {
        this.plantType = plantType;
        this.period = period;
    }
}
