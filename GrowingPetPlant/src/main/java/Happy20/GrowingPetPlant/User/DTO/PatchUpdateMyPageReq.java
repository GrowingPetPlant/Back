package Happy20.GrowingPetPlant.User.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchUpdateMyPageReq {

    private String id;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호를 8~16자리로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]*$",
            message = "비밀번호는 영문 소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}",
            message = "전화번호 형식은 000-0000-0000으로 입력해주세요.")
    private String phoneNumber;


    @Builder
    public PatchUpdateMyPageReq(String id, String password, String userName, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }
}
