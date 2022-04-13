package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.model.Image;
import Backend.NobbieFinal.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository repos;

    public ImageServiceImpl(ImageRepository repos) {
        this.repos = repos;
    }

    @Override
    public Image saveImg(ImageDto imageDto) {
        Image img = new Image();
        img.setId(imageDto.getId());
        img.setContent(imageDto.getContent());
        return this.repos.save(img);
    }
}