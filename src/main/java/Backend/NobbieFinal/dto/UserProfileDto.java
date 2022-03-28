package Backend.NobbieFinal.dto;

import Backend.NobbieFinal.model.ProfileType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserProfileDto {
    private final Long userId;
    @NotBlank
    private final String firstname;
    @NotBlank
    private final String lastname;
    @Email
    private final String emailaddress;
    @NotBlank
    @Size(min=6, max=10)
    private final String password;
    @NotBlank
    private final ProfileType profileType;
}
