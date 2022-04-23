package nl.novi.nobbie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nl.novi.nobbie.model.UserProfile;
import javax.validation.constraints.NotNull;

@Data
public class ImageDto {
    private final Long id;

    @NotNull(message = "Content can not be empty")
    public byte[] content;

    @JsonIgnore
    private final UserProfile user;
}
