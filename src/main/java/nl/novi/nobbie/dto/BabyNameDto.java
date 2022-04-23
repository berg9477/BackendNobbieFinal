package nl.novi.nobbie.dto;

import nl.novi.nobbie.model.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BabyNameDto {
    private final Long id;

    @NotBlank
    private final String name;

    @NotNull
    private final Gender gender;

    private final int listingNumber;
}
