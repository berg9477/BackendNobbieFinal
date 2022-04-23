package nl.novi.nobbie.dto;

import nl.novi.nobbie.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserProfileDto {
    private final Long userId;
    @NotBlank(message = "Username can not be empty")
    @Size(min=4, max=12, message = "Username must be between 4 and 12 characters long")
    private final String username;
    @NotBlank(message = "Firstname can not be empty")
    private final String firstname;
    @NotBlank(message = "Lastname can not be empty")
    private final String lastname;
    @Email(message = "Please enter a valid emailaddress")
    @NotBlank(message = "Emailaddress can not be empty")
    private final String emailaddress;
    @NotBlank(message = "Password can not be empty")
    @Size(min=6, max=10, message = "Password must have a minimum of 6 and maximum of 10 characters")
    private final String password;
    @NotNull(message = "Role can not be empty")
    private final Role role;
    @NotNull(message = "Enabled can not be empty")
    private final int enabled;
}
