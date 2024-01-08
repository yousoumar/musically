package fr.imt.musically.song;

import fr.imt.musically.singer.Singer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    private Song song;
    private Singer singer;

    @BeforeEach
    void setUp() {
        singer = Mockito.mock(Singer.class);
        song = new Song("Test Song", 2022, 4.5, singer);
    }

    @Test
    void shouldReturnSongId() {
        UUID songId = song.getSongId();
        assertNotNull(songId);
    }

    @Test
    void shouldSetAndGetTitle() {
        song.setTitle("New Title");
        assertEquals("New Title", song.getTitle());
    }

    @Test
    void shouldSetAndGetYear() {
        song.setYear(2023);
        assertEquals(2023, song.getYear());
    }

    @Test
    void shouldSetAndGetRating() {
        song.setRating(5.0);
        assertEquals(5.0, song.getRating());
    }

    @Test
    void shouldAddSinger() {
        Singer newSinger = Mockito.mock(Singer.class);
        song.addSinger(newSinger);
        assertTrue(song.getSingers().contains(newSinger));
    }

    @Test
    void shouldRemoveSinger() {
        song.removeSinger(singer);
        assertFalse(song.getSingers().contains(singer));
    }

    @Test
    void shouldReturnSingers() {
        Set<Singer> singers = new HashSet<>();
        singers.add(singer);
        assertEquals(singers, song.getSingers());
    }
}