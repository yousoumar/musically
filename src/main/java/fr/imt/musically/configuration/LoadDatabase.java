package fr.imt.musically.configuration;


import fr.imt.musically.singer.Singer;
import fr.imt.musically.singer.SingerRepository;
import fr.imt.musically.song.Song;
import fr.imt.musically.song.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private final Environment env;

    public LoadDatabase(Environment env) {
        this.env = env;
    }

    @Bean
    public CommandLineRunner initDatabase(SingerRepository singerRepository, SongRepository songRepository) {


        log.info("Initializing database");
        return args -> {

            Singer nancy = new Singer("Nancy", "Ajram");
            Singer taylor = new Singer("Taylor", "Swift");
            Singer adele = new Singer("Adele", "Adkins");
            Singer ed = new Singer("Ed", "Sheeran");
            Singer shakira = new Singer("Shakira", "Mebarak");

            Song song1 = new Song("Ya Tabtab Wa Dalla", 2006, 5.0, nancy);
            Song song2 = new Song("Ana Yalli Bahebak", 2008, 4.5, nancy);
            Song song3 = new Song("Me Enamoré", 2017, 4.0, shakira);
            Song song4 = new Song("Hips Don't Lie", 2006, 4.5, shakira);
            Song song5 = new Song("Hello", 2015, 4.5, adele);
            Song song6 = new Song("Someone Like You", 2011, 4.5, adele);
            Song song7 = new Song("Shape of You", 2017, 4.5, ed);
            Song song8 = new Song("Perfect", 2017, 4.5, ed);
            Song song9 = new Song("Love Story", 2008, 4.5, taylor);
            Song song10 = new Song("You Belong With Me", 2008, 4.5, taylor);

            songRepository.saveAll(List.of(song1, song2, song3, song4, song5, song6, song7, song8, song9, song10));


        };
    }

}

