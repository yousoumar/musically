package fr.imt.musically.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Song findBySongId(UUID songId);

    Song findByTitleAndYear(String title, Integer year);

    Set<Song> findAllBySingersIsEmpty();
}
