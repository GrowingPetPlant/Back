package Happy20.GrowingPetPlant.Status;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;


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

    private Timestamp light;

    @Column(name = "plant_number")
    private Long plantNumber;

    @Column(name = "growing_date")
    private Date growingDate;

    @Column(name = "watering_date")
    private Date wateringDate;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public Status(Long moisture, Long temperature, Long humidity, Timestamp light, Long plantNumber, Date growingDate, Date wateringDate) {
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.plantNumber = plantNumber;
        this.growingDate = growingDate;
        this.wateringDate = wateringDate;
    }

    public void setWateringDate(Date wateringDate) {
        this.wateringDate = wateringDate;
    }
}