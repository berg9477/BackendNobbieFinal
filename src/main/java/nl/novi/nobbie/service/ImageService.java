package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.ImageDto;
import nl.novi.nobbie.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImg(Long id, MultipartFile file);
    ImageDto findById(Long id) throws Exception;
}
