package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.ImageDto;
import Backend.NobbieFinal.model.Image;
import Backend.NobbieFinal.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public ImageDto findById(Long id) {
        Image img = new Image();
        img = this.repos.findById(id).get();
        ImageDto imageDto = new ImageDto(img.getId());
        imageDto.setContent(img.getContent());
        return imageDto;
    }
}