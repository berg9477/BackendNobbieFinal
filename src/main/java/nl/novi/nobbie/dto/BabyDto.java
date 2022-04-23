package nl.novi.nobbie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.model.UserProfile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BabyDto {
    private final Long id;

    @NotBlank(message = "Nickname can not be empty")
    private final String nickname;

    @NotNull(message = "Gender can not be empty")
    private final Gender gender;

    @NotNull(message = "Birthdate can not be empty")
    private final LocalDate birthdate;

    @NotNull(message = "Expected can not be empty")
    private final Boolean expected;

    @JsonIgnore
    private final UserProfile user;
}
