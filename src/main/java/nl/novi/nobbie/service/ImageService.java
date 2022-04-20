package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String saveImg(Long id, MultipartFile file);
    public ImageDto findById(Long id);
}
