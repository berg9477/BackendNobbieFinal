package Backend.NobbieFinal.repository;

import Backend.NobbieFinal.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
