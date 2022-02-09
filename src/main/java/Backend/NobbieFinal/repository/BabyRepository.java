package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.Baby;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BabyRepository extends JpaRepository<Baby, Long> {
}
