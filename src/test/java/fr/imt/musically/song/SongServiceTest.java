package fr.imt.musically.song;


import fr.imt.musically.request.AddSingerRequestBody;
import fr.imt.musically.request.BodyValidator;
import fr.imt.musically.request.SongRequestBody;
import fr.imt.musically.singer.Singer;
import fr.imt.musically.singer.SingerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SongServiceTest {

    @MockBean
    private SongRepository songRepository;

    @MockBean
    private SingerRepository singerRepository;

    @MockBean
    private BodyValidator bodyValidator;

    private SongService songService;

    @BeforeEach
    void setUp() {
        songService = new SongService(songRepository, singerRepository, bodyValidator);
    }

    @Test
    void shouldReturnAllSongs() {
        Song song = new Song("Test Song", 2022, 4.5);
        when(songRepository.findAll()).thenReturn(Collections.singletonList(song));

        List<Song> songs = songService.getAllSongs();

        assertFalse(songs.isEmpty());
        assertEquals(song, songs.getFirst());
    }

    @Test
    void shouldCreateSongs() {
        Singer singer = new Singer("John", "Doe");
        SongRequestBody songRequestBody = new SongRequestBody("Test Song", 2022, 4.5);
        when(songRepository.findByTitleAndYear(any(), any())).thenReturn(null);
        when(songRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Set<Song> songs = songService.createSongs(singer, songRequestBody);

        assertFalse(songs.isEmpty());
        assertEquals(songRequestBody.getTitle(), songs.iterator().next().getTitle());
    }

    @Test
    void shouldThrowExceptionWhenCreatingSongWithExistingTitleAndYear() {
        Singer singer = new Singer("John", "Doe");
        SongRequestBody songRequestBody = new SongRequestBody("Test Song", 2022, 4.5);
        when(songRepository.findByTitleAndYear(any(), any())).thenReturn(new Song());

        assertThrows(IllegalArgumentException.class, () -> songService.createSongs(singer, songRequestBody));
    }

    @Test
    void shouldAddSingers() {
        Song song = new Song("Test Song", 2022, 4.5);
        Singer singer = new Singer("John", "Doe");
        AddSingerRequestBody addSingerRequestBody = new AddSingerRequestBody(singer.getSingerId().toString());
        when(songRepository.findBySongId(any())).thenReturn(song);
        when(singerRepository.findBySingerId(any())).thenReturn(singer);
        when(songRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Song updatedSong = songService.addSingers(song.getSongId().toString(), Collections.singletonList(addSingerRequestBody));

        assertFalse(updatedSong.getSingers().isEmpty());
        assertEquals(singer, updatedSong.getSingers().iterator().next());
    }

    @Test
    void shouldThrowExceptionWhenAddingSingerToNonExistingSong() {
        Singer singer = new Singer("John", "Doe");
        AddSingerRequestBody addSingerRequestBody = new AddSingerRequestBody(singer.getSingerId().toString());
        when(songRepository.findBySongId(any())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> songService.addSingers(UUID.randomUUID().toString(), Collections.singletonList(addSingerRequestBody)));
    }

    @Test
    void shouldThrowExceptionWhenAddingNonExistingSingerToSong() {
        Song song = new Song("Test Song", 2022, 4.5);
        AddSingerRequestBody addSingerRequestBody = new AddSingerRequestBody(UUID.randomUUID().toString());
        when(songRepository.findBySongId(any())).thenReturn(song);
        when(singerRepository.findBySingerId(any())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> songService.addSingers(song.getSongId().toString(), Collections.singletonList(addSingerRequestBody)));
    }
}