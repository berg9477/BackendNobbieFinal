package Backend.NobbieFinal.dto;

import Backend.NobbieFinal.model.MediaType;
import Backend.NobbieFinal.model.UserProfile;
import lombok.Data;

@Data
public class SocialMediaAccountDto {
    private final Long id;
    private final UserProfile userId;
    private final MediaType socialMediaType;
}
