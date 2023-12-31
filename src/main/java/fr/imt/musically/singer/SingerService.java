package fr.imt.musically.singer;

import fr.imt.musically.request.BodyValidator;
import fr.imt.musically.request.SingerRequestBody;
import fr.imt.musically.song.Song;
import fr.imt.musically.request.SongRequestBody;
import fr.imt.musically.song.SongRepository;
import fr.imt.musically.song.SongService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SingerService {
    private final SingerRepository repository;

    private final SongRepository songRepository;

    private final SongService songService;

    private final BodyValidator bodyValidator;

    public SingerService(SingerRepository repository, SongRepository songRepository, SongService songService, BodyValidator bodyValidator) {
        this.repository = repository;
        this.songRepository = songRepository;
        this.songService = songService;
        this.bodyValidator = bodyValidator;
    }

    public List<Singer> getAllSingers(String firstName, String lastName) {
        firstName = Optional.ofNullable(firstName).map(String::trim).orElse(null);
        lastName = Optional.ofNullable(lastName).map(String::trim).orElse(null);

        return
            (firstName != null || lastName != null) ?
                repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName) :
                repository.findAll();
    }

    public Singer getSinger(String singerId, Double songMinimumRating) {
        Singer singer = repository.findBySingerId(UUID.fromString(singerId));

        if (singer == null) {
            throw new IllegalArgumentException("This singer doesn't exist");
        }

        Set<Song> filteredSongs = singer.getSongs().stream()
                .filter(song -> song.getRating() >= songMinimumRating)
                .collect(Collectors.toSet());

        singer.setSongs(filteredSongs);
        
        return singer;
    }

    private Singer getSingerFromBodyRequest(SingerRequestBody singerBody){
        bodyValidator.validateBodyRequest(singerBody);
        return repository.findByFirstNameAndLastName(singerBody.getFirstName(), singerBody.getLastName());
    }

    public Singer createSinger(SingerRequestBody singer) throws IllegalArgumentException {

        if (getSingerFromBodyRequest(singer) != null) {
            throw new IllegalArgumentException("This singer already exists");
        }

        return repository.save(new Singer(singer.getFirstName(), singer.getLastName()));
    }

    public void deleteSinger(SingerRequestBody singerBody) throws IllegalArgumentException {
        Singer singer = getSingerFromBodyRequest(singerBody);
        if(singer == null){
            throw new IllegalArgumentException("This singer doesn't exist");
        }

        repository.delete(singer);
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

        if(!singer.getSongs().contains(song)){
            throw new IllegalArgumentException("This singer doesn't have this song");
        }

        song.setRating(rating);

        return songRepository.save(song);
    }

    public Singer addSongs(String singerId, SongRequestBody... songBody) throws ConstraintViolationException{
        Singer singer = repository.findBySingerId(UUID.fromString(singerId));
        if(singer == null){
            throw new IllegalArgumentException("This singer doesn't exist");
        }

        songService.createSongs(singer, songBody);

        return singer;
    }


}
