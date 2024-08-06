package Happy20.GrowingPetPlant.Status.Domain;

import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "status")
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_number")
    private Long statusNumber;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "moisture")
    private Double moisture; // 토양 습도

    @Column(name = "humidity")
    private Double humidity; // 대기 습도

    @Column(name = "light")
    private Boolean light;

    @Column(name = "fan")
    private Boolean fan;

    @Column(name = "watering")
    private Boolean watering;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plant_number")
    @JsonBackReference
    private UserPlant userPlant;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public Status(Double moisture, Double temperature, Double humidity, Boolean light, Boolean fan,  Boolean watering, UserPlant userPlant) {
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.fan = fan;
        this.watering = watering;
        this.userPlant = userPlant;
        this.createTime = LocalDateTime.now();
    }
}