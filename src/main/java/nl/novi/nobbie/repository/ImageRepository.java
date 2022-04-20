package nl.novi.nobbie.repository;

import nl.novi.nobbie.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
