package fr.imt.musically.singer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SingerRepository extends JpaRepository<Singer, Long> {

    Singer findByFirstNameAndLastName(String firstName, String lastName);

    List<Singer> findByFirstName(String firstName);

    List<Singer> findByLastName(String lastName);

    Singer findBySingerId(UUID singerId);

    // Find all singers that contain the given string in their first name or last name by ignoring case
    List<Singer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
