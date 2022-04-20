package nl.novi.nobbie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nl.novi.nobbie.model.UserProfile;

import javax.validation.constraints.NotBlank;

@Data
public class ImageDto {
    private final Long id;

    @NotBlank
    public byte[] content;

    @JsonIgnore
    private final UserProfile user;
}
