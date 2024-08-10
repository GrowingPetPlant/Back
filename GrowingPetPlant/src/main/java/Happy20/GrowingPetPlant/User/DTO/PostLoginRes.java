package Happy20.GrowingPetPlant.User.DTO;

import Happy20.GrowingPetPlant.JWT.TokenDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLoginRes {
    Long userId;

    String message;

    TokenDto token;

    @Builder
    public PostLoginRes(Long userId, String message, TokenDto token) {
        this.userId = userId;
        this.message = message;
        this.token = token;
    }
}
