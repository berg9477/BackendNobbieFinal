package nl.novi.nobbie.repository;

import nl.novi.nobbie.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Boolean existsBy(String emailaddress);
}
