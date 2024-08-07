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

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 3, max = 10, message = "아이디를 3~10자리로 입력해주세요.")
    @Pattern(regexp="^(?=.*[a-z])[a-zA-Z0-9]*$", message = "아이디는 영문자와 숫자로 입력해주세요.")
    @Column(name = "id")
    private String id;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호를 8~16자리로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]*$",
            message = "비밀번호는 영문 소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}",
            message = "전화번호 형식은 000-0000-0000으로 입력해주세요.")
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