package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
