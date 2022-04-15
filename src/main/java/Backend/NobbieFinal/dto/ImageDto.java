package Backend.NobbieFinal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImageDto {
    private final Long id;

    @NotBlank
    public byte[] content;

}
