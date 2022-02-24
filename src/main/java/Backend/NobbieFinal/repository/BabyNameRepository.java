package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.BabyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyNameRepository extends JpaRepository<BabyName, Long> {
    List<BabyName> findBabyNameByNameStartingWith(Character ch);
    List<BabyName> findBabyNameByNameContaining(String input);
}
