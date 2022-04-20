package nl.novi.nobbie.repository;

import nl.novi.nobbie.model.SocialMediaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMediaAccountRepository extends JpaRepository<SocialMediaAccount, Long> {
}
