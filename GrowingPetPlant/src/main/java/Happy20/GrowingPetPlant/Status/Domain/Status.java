package Happy20.GrowingPetPlant.Status.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data // Data : getter, setter 포함
@Entity
@Table(name = "STATUS")
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 자동으로 생성(값을 직접 입력할 필요 X, pk이기 때문에)
    @Column(name = "status_number")
    private Long statusNumber;

    private Long moisture;

    private Long temperature;

    private Long humidity;

    private Boolean light;

    @Column(name = "plant_number")
    private Long plantNumber;

    @Column(name = "growing_date")
    private LocalDate growingDate;

    @Column(name = "watering_date")
    private LocalDate wateringDate;

    private Boolean fan;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 엔티티가 저장되기 전에 호출되는 메서드
    @PrePersist
    protected void onCreate() {
//        this.growingDate = LocalDate.now();
        this.createTime = LocalDateTime.now();
    }

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public Status(Long moisture, Long temperature, Long humidity, Boolean light, Long plantNumber,
                  LocalDate growingDate, LocalDate wateringDate, Boolean fan, LocalDateTime createTime) {
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.plantNumber = plantNumber;
        this.growingDate = growingDate;
        this.wateringDate = wateringDate;
        this.fan = fan;
        this.createTime = createTime;
    }

    public void setWateringDate(LocalDate wateringDate) {
        this.wateringDate = wateringDate;
    }
}