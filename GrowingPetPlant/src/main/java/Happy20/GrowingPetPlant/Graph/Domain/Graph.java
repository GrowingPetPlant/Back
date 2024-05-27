package Happy20.GrowingPetPlant.Graph.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "GRAPH")
@NoArgsConstructor
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "graph_number")
    private Long graphNumber;

    @Column(name = "temp_dawn")
    private float tempDawn;

    @Column(name = "temp_morning")
    private float tempMorning;

    @Column(name = "temp_day")
    private float tempDay;

    @Column(name = "temp_night")
    private float tempNight;

    @Column(name = "moisture_dawn")
    private float moistureDawn;

    @Column(name = "moisture_morning")
    private float moistureMorning;

    @Column(name = "moisture_day")
    private float moistureDay;

    @Column(name = "moisture_night")
    private float moistureNight;

    @Column(name = "humi_dawn")
    private float humiDawn;

    @Column(name = "humi_morning")
    private float humiMorning;

    @Column(name = "humi_day")
    private float humiDay;

    @Column(name = "humi_night")
    private float humiNight;

    private LocalDate date;

    @Builder
    public Graph(float tempDawn, float tempMorning, float tempDay, float tempNight,
                 float moistureDawn, float moistureMorning, float moistureDay, float moistureNight,
                 float humiDawn, float humiMorning, float humiDay, float humiNight, LocalDate date)
    {
        this.tempDawn = tempDawn;
        this.tempMorning = tempMorning;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.moistureDawn = moistureDawn;
        this.moistureMorning = moistureMorning;
        this.moistureDay = moistureDay;
        this.moistureNight = moistureNight;
        this.humiDawn = humiDawn;
        this.humiMorning = humiMorning;
        this.humiDay = humiDay;
        this.humiNight = humiNight;
        this.date = date;
    }

}