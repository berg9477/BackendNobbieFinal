package Backend.NobbieFinal.dto;

import Backend.NobbieFinal.model.MediaType;
import lombok.Data;

@Data
public class SocialMediaAccountDto {
    private final Long id;
    private final Long userId;
    private final MediaType socialMediaType;
}
