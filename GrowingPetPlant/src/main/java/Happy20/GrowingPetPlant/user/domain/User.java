package Happy20.GrowingPetPlant.user.domain;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "gpp_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    public User(String name) {
        this.name = name;
    }

}
