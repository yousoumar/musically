package fr.imt.musically.singer;


import fr.imt.musically.request.BodyValidator;
import fr.imt.musically.request.SingerRequestBody;
import fr.imt.musically.request.SongRequestBody;
import fr.imt.musically.song.Song;
import fr.imt.musically.song.SongRepository;
import fr.imt.musically.song.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SingerServiceTest {

    @MockBean
    private SingerRepository singerRepository;

    @MockBean
    private SongRepository songRepository;

    @MockBean
    private SongService songService;

    @MockBean
    private BodyValidator bodyValidator;

    private SingerService singerService;

    @BeforeEach
    void setUp() {
        singerService = new SingerService(singerRepository, songRepository, songService, bodyValidator);
    }

    @Test
    void shouldReturnAllSingers() {
        Singer singer = new Singer("John", "Doe");
        when(singerRepository.findAll()).thenReturn(Collections.singletonList(singer));

        List<Singer> singers = singerService.getAllSingers(null, null);

        assertFalse(singers.isEmpty());
        assertEquals(singer, singers.getFirst());
    }

    @Test
    void shouldCreateSinger() {
        SingerRequestBody singerRequestBody = new SingerRequestBody("John", "Doe");
        when(singerRepository.findByFirstNameAndLastName(any(), any())).thenReturn(null);
        when(singerRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Singer singer = singerService.createSinger(singerRequestBody);

        assertNotNull(singer);
        assertEquals(singerRequestBody.getFirstName(), singer.getFirstName());
        assertEquals(singerRequestBody.getLastName(), singer.getLastName());
    }

    @Test
    void shouldThrowExceptionWhenCreatingExistingSinger() {
        SingerRequestBody singerRequestBody = new SingerRequestBody("John", "Doe");
        when(singerRepository.findByFirstNameAndLastName(any(), any())).thenReturn(new Singer());

        assertThrows(IllegalArgumentException.class, () -> singerService.createSinger(singerRequestBody));
    }

    @Test
    void shouldDeleteSinger() {
        Singer singer = new Singer("John", "Doe");
        when(singerRepository.findBySingerId(any())).thenReturn(singer);
        Mockito.doNothing().when(singerRepository).delete(any());

        assertDoesNotThrow(() -> singerService.deleteSinger(singer.getSingerId().toString()));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingSinger() {
        SingerRequestBody singerRequestBody = new SingerRequestBody("John", "Doe");
        when(singerRepository.findByFirstNameAndLastName(any(), any())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> singerService.deleteSinger(UUID.randomUUID().toString()));
    }

    @Test
    void shouldUpdateSongRatingOfASinger() {
        Singer singer = new Singer("John", "Doe");
        Song song = new Song("Test Song", 2022, 4.5, singer);
        singer.addSong(song);
        when(singerRepository.findBySingerId(any())).thenReturn(singer);
        when(songRepository.findBySongId(any())).thenReturn(song);
        when(songRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Song updatedSong = singerService.updateSongRatingOfASinger(singer.getSingerId().toString(), song.getSongId().toString(), 5.0);

        assertEquals(5.0, updatedSong.getRating());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingSongRatingOfNonExistingSinger() {
        Song song = new Song("Test Song", 2022, 4.5);
        when(singerRepository.findBySingerId(any())).thenReturn(null);
        when(songRepository.findBySongId(any())).thenReturn(song);

        assertThrows(IllegalArgumentException.class, () -> singerService.updateSongRatingOfASinger(UUID.randomUUID().toString(), song.getSongId().toString(), 5.0));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingSongRatingOfSingerWithoutSong() {
        Singer singer = new Singer("John", "Doe");
        Song song = new Song("Test Song", 2022, 4.5);
        when(singerRepository.findBySingerId(any())).thenReturn(singer);
        when(songRepository.findBySongId(any())).thenReturn(song);

        assertThrows(IllegalArgumentException.class, () -> singerService.updateSongRatingOfASinger(singer.getSingerId().toString(), song.getSongId().toString(), 5.0));
    }

    @Test
    void shouldAddSongs() {
        Singer singer = new Singer("John", "Doe");
        SongRequestBody songRequestBody = new SongRequestBody("Test Song", 2022, 4.5);
        when(singerRepository.findBySingerId(any())).thenReturn(singer);
        when(songService.createSongs(any(), any())).thenReturn(Collections.singleton(new Song(songRequestBody.getTitle(), songRequestBody.getYear(), songRequestBody.getRating(), singer)));

        Singer updatedSinger = singerService.addSongs(singer.getSingerId().toString(), songRequestBody);

        assertFalse(updatedSinger.getSongs().isEmpty());
        assertEquals(songRequestBody.getTitle(), updatedSinger.getSongs().iterator().next().getTitle());
    }

    @Test
    void shouldThrowExceptionWhenAddingSongsToNonExistingSinger() {
        SongRequestBody songRequestBody = new SongRequestBody("Test Song", 2022, 4.5);
        when(singerRepository.findBySingerId(any())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> singerService.addSongs(UUID.randomUUID().toString(), songRequestBody));
    }
}
