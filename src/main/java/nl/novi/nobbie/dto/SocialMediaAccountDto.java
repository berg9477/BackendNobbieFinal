package nl.novi.nobbie.dto;

import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.UserProfile;
import lombok.Data;

@Data
public class SocialMediaAccountDto {
    private final Long id;

    private final UserProfile user;

    private final MediaType socialMediaType;

}
