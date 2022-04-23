package nl.novi.nobbie.dto;

import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.UserProfile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SocialMediaAccountDto {
    private final Long id;

    private final UserProfile user;

    @NotNull
    private final MediaType socialMediaType;

}
