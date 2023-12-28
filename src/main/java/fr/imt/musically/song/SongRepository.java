package fr.imt.musically.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Song findByTitle(String title);

    Song findBySongId(UUID songId);
}
