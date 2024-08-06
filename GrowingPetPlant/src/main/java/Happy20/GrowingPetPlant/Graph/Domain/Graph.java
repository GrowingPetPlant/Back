package Happy20.GrowingPetPlant.Graph.Domain;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "graph")
@NoArgsConstructor
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "graph_number")
    private Long graphNumber;

    @Column(name = "temp_dawn")
    private Double tempDawn;

    @Column(name = "temp_morning")
    private Double tempMorning;

    @Column(name = "temp_day")
    private Double tempDay;

    @Column(name = "temp_night")
    private Double tempNight;

    @Column(name = "moisture_dawn")
    private Double moistureDawn;

    @Column(name = "moisture_morning")
    private Double moistureMorning;

    @Column(name = "moisture_day")
    private Double moistureDay;

    @Column(name = "moisture_night")
    private Double moistureNight;

    @Column(name = "humi_dawn")
    private Double humiDawn;

    @Column(name = "humi_morning")
    private Double humiMorning;

    @Column(name = "humi_day")
    private Double humiDay;

    @Column(name = "humi_night")
    private Double humiNight;

    @Column(name = "graph_date")
    private LocalDate graphDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plant_number")
    @JsonBackReference
    private UserPlant userPlant;

    @Builder
    public Graph(LocalDate graphDate)
    {
        this.tempDawn = 0D;
        this.tempMorning = 0D;
        this.tempDay = 0D;
        this.tempNight = 0D;
        this.moistureDawn = 0D;
        this.moistureMorning = 0D;
        this.moistureDay = 0D;
        this.moistureNight = 0D;
        this.humiDawn = 0D;
        this.humiMorning = 0D;
        this.humiDay = 0D;
        this.humiNight = 0D;
        this.graphDate = graphDate;
    }

}