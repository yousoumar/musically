package fr.imt.musically.singer;

import fr.imt.musically.song.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SingerTest {

    private Singer singer;
    private Song song;

    @BeforeEach
    void setUp() {
        singer = new Singer("John", "Doe");
        song = Mockito.mock(Song.class);
    }

    @Test
    void shouldReturnFullName() {
        assertEquals("John Doe", singer.getFullName());
    }

    @Test
    void shouldAddSong() {
        singer.addSong(song);
        assertTrue(singer.getSongs().contains(song));
    }

    @Test
    void shouldRemoveSong() {
        singer.addSong(song);
        singer.removeSong(song);
        assertFalse(singer.getSongs().contains(song));
    }

    @Test
    void shouldReturnSingerId() {
        UUID singerId = singer.getSingerId();
        assertNotNull(singerId);
    }

    @Test
    void shouldSetAndGetFirstName() {
        singer.setFirstName("Jane");
        assertEquals("Jane", singer.getFirstName());
    }

    @Test
    void shouldSetAndGetLastName() {
        singer.setLastName("Smith");
        assertEquals("Smith", singer.getLastName());
    }

    @Test
    void shouldSetAndGetSongs() {
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        singer.setSongs(songs);
        assertEquals(songs, singer.getSongs());
    }
}