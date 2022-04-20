package nl.novi.nobbie.dto;

import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class BabyDto {
    private final Long id;

    @NotBlank
    private final String nickname;

    private final Gender gender;

    private final LocalDate birthdate;

    private final Boolean expected;

    @JsonIgnore
    private final UserProfile user;
}
