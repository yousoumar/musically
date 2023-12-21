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

import java.util.*;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(SingerRepository singerRepository, SongRepository songRepository) {
        log.info("Initializing database");
        return args -> {
            singerRepository.save(new Singer("Nancy", "Ajram"));
            singerRepository.save( new Singer("Taylor", "Swift"));
            singerRepository.save(new Singer("Adele", "Adkins"));
            singerRepository.save(new Singer("Ed", "Sheeran"));
            singerRepository.save(new Singer("Eminem", "Mathers"));

            List<Singer> singers = singerRepository.findAll();
            songRepository.save(new Song("Ya Tabtab Wa Dalla", 2001, 4.5, singers.get(0)));
        };
    }

}

