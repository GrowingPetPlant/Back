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

    @Column(name = "growth_period")
    private Long growthPeriod;

    @Column(name = "high_temp")
    private Long highTemp;

    @Column(name = "low_temp")
    private Long lowTemp;

    @Column(name = "opt_moisture")
    private Long optMoisture;

    @Builder
    public Plant(String plantType, Long growthPeriod, Long highTemp,  Long lowTemp, Long optMoisture) {
        this.plantType = plantType;
        this.growthPeriod = growthPeriod;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.optMoisture = optMoisture;
    }
}
