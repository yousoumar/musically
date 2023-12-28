package fr.imt.musically.singer;

import fr.imt.musically.song.Song;
import fr.imt.musically.song.SongRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class SingerService {
    private final SingerRepository repository;

    private final SongRepository songRepository;

    private final Validator validator;

    public SingerService(SingerRepository repository, Validator validator, SongRepository songRepository) {
        this.repository = repository;
        this.songRepository = songRepository;
        this.validator = validator;
    }

    public List<Singer> getAllSingers(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            Singer singer = repository.findByFirstNameAndLastName(firstName, lastName);

            return singer != null ? List.of(singer) : List.of();
        }
        
        if (firstName != null) {
            return repository.findByFirstName(firstName);
        }
        
        if (lastName != null) {
            return repository.findByLastName(lastName);
        }
        
        return repository.findAll();
    }

    public Singer createSinger(SingerBodyRequest singer) throws IllegalArgumentException {
        
        if (getSingerFromBodyRequest(singer) != null) {
            throw new IllegalArgumentException("This singer already exists");
        }

        return repository.save(new Singer(singer.getFirstName(), singer.getLastName()));
    }

    public void deleteSinger(SingerBodyRequest singerBody) throws IllegalArgumentException {
        Singer singer = getSingerFromBodyRequest(singerBody);
        if(singer == null){
            throw new IllegalArgumentException("This singer doesn't exist");
        }

        repository.delete(singer);
    }

    private Singer getSingerFromBodyRequest(SingerBodyRequest singerBody) {
        Set<ConstraintViolation<SingerBodyRequest>> violations = validator.validate(singerBody);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<SingerBodyRequest> violation : violations) {
                sb.append(violation.getMessage());
                sb.append("\n");
            }

            throw new ConstraintViolationException(sb.toString(), violations);
        }

        return repository.findByFirstNameAndLastName(singerBody.getFirstName(), singerBody.getLastName());
    }

    public Song updateSongRatingOfASinger(String singerId, String songId, Double rating) {
        Singer singer = repository.findBySingerId(UUID.fromString(singerId));
        Song song = songRepository.findBySongId(UUID.fromString(songId));

        if (singer == null) {
            throw new IllegalArgumentException("This singer doesn't exist");
        }

        if (song == null) {
            throw new IllegalArgumentException("This song doesn't exist");
        }

        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("The rating must be between 0 and 5");
        }

        song.setRating(rating);

        return songRepository.save(song);
    }
}
