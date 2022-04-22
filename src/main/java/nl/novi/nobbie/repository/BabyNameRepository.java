package nl.novi.nobbie.repository;

import nl.novi.nobbie.model.BabyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyNameRepository extends JpaRepository<BabyName, Long> {
    List<BabyName> findBabyNameByNameStartingWith(Character ch);
    List<BabyName> findBabyNameByNameContaining(String input);
    boolean existsByName(String name);
}
