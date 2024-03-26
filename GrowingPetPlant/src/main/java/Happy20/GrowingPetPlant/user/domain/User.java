package Happy20.GrowingPetPlant.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_number;

    private String id;

    private String password;

    private String user_name;

    private String phone_number;

    @Builder
    public User(String id, String password, String user_name, String phone_number) {
        this.id = id;
        this.password = password;
        this.user_name = user_name;
        this.phone_number = phone_number;
    }
}