package Happy20.GrowingPetPlant.User.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Data : getter, setter 포함
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 자동으로 생성(값을 직접 입력할 필요 X, pk이기 때문에)
    @Column(name = "user_number")
    private Long userNumber;

    private String id;

    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public User(String id, String password, String userName, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }
}