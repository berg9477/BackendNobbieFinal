package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImg(Long id, MultipartFile file) throws Exception;
    ImageDto findById(Long id) throws Exception;
}
