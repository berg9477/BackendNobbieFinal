package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.model.Image;

import java.util.Map;

public interface ImageService {
    public Image saveImg(ImageDto imageDto);
    public ImageDto findById(Long id);
}
