package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.model.Image;

public interface ImageService {
    public Image saveImg(ImageDto imageDto);
}
