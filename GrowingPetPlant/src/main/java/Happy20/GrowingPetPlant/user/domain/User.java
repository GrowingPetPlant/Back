package Happy20.GrowingPetPlant.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//data : getter, setter 포함
@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //db가 자동으로 생성(값을 직접 입력할 필요 X, pk이기 때문에)
    private Long user_number;

    private String id;
    private String password;
    private String user_name;
    private String phone_number;

    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor)
    //생성자
    public User(String id, String password, String userName, String phoneNumber){
        this.id = id;
        this.password = password;
        this.user_name = userName;
        this.phone_number = phoneNumber;
    }

}
