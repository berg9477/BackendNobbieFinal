package nl.novi.nobbie.repository;

import nl.novi.nobbie.model.Baby;
import nl.novi.nobbie.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BabyRepository extends JpaRepository<Baby, Long> {
    List<Baby> findByUser(UserProfile user);
}
