package fr.imt.musically.song;

import fr.imt.musically.request.AddSingerRequestBody;
import fr.imt.musically.request.BodyValidator;
import fr.imt.musically.request.SongRequestBody;
import fr.imt.musically.singer.Singer;
import fr.imt.musically.singer.SingerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class SongService {

    private final SingerRepository singerRepository;
    private final SongRepository songRepository;

    private final BodyValidator bodyValidator;

    public SongService(SongRepository songRepository, SingerRepository singerRepository, BodyValidator bodyValidator) {
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.bodyValidator = bodyValidator;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // It is always transactional with JPA
    @Transactional(rollbackOn = Exception.class)
    public Set<Song> createSongs(Singer singer, SongRequestBody... songBody) {

        for (SongRequestBody song : songBody) {
            bodyValidator.validateBodyRequest(song);

            // Check if the song already exists
            if (songRepository.findByTitleAndYear(song.getTitle(), song.getYear()) != null) {
                throw new IllegalArgumentException("This song already exists");
            }

            Song s = new Song(song.getTitle(), song.getYear(), song.getRating(), singer);

            songRepository.save(s);

        }

        return singer.getSongs();
    }

    @Transactional(rollbackOn = Exception.class)
    public Song addSingers(String songId, List<AddSingerRequestBody> singersBody) {
        Song song = songRepository.findBySongId(UUID.fromString(songId));

        if (song == null) {
            throw new IllegalArgumentException("This song does not exist");
        }

        for (AddSingerRequestBody singerBody : singersBody) {
            bodyValidator.validateBodyRequest(singerBody);
        }

        for (AddSingerRequestBody singerBody : singersBody) {
            Singer singer = singerRepository.findBySingerId(UUID.fromString(singerBody.getSingerId()));

            if (singer == null) {
                throw new IllegalArgumentException("This singer does not exist");
            }
            song.addSinger(singer);
            songRepository.save(song);
        }

        return song;
    }
}
