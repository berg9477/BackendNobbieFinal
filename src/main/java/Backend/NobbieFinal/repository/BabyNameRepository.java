package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.BabyName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyNameRepository extends JpaRepository<BabyName, Long> {
}
