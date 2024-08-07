package Happy20.GrowingPetPlant.User.Domain;

import Happy20.GrowingPetPlant.User.Authority;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static Happy20.GrowingPetPlant.User.Authority.ROLE_USER;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 자동으로 생성(값을 직접 입력할 필요 X, pk이기 때문에)
    @Column(name = "user_number")
    private Long userNumber;

    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<UserPlant> plantsList = new ArrayList<>();

    @Column(name = "authority")
    private Authority authority;

    @Builder
    //인수없는 생성자 자동으로 생성됨(NoArgsConstructor), 생성자
    public User(String id, String password, String userName, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.authority = ROLE_USER;
    }
}