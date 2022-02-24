package Backend.NobbieFinal.dto;

import Backend.NobbieFinal.model.Gender;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

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

    @NumberFormat
    private final int weeksLeft;

    @NumberFormat
    private final Long userId;
}