package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BabyRepository extends JpaRepository<Baby, Long> {
    List<Baby> findByUser(UserProfile user);
}
