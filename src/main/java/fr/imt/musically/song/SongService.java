package fr.imt.musically.song;

import fr.imt.musically.singer.Singer;
import fr.imt.musically.singer.SingerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final SingerRepository singerRepository;

    private final Validator validator;

    public SongService(SongRepository repository, SingerRepository singerRepository, Validator validator){
        this.songRepository = repository;
        this.singerRepository = singerRepository;
        this.validator = validator;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // It is always transactional with JPA
    @Transactional(rollbackOn = Exception.class)
    public Set<Song> addSongs(Singer singer, List<SongBodyRequest> songBody) {

        for (SongBodyRequest song : songBody) {
            Set<ConstraintViolation<SongBodyRequest>> violations = validator.validate(song);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<SongBodyRequest> violation : violations) {
                    sb.append(violation.getMessage());
                    sb.append("\n");
                }

                throw new ConstraintViolationException(sb.toString(), violations);
            }

            Song s = new Song(song.getTitle(), song.getYear(), song.getRating(), singer);

            songRepository.save(s);

            }

        return singer.getSongs();

    }

}
