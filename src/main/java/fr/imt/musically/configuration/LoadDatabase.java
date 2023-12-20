package fr.imt.musically.configuration;


import fr.imt.musically.singer.Singer;
import fr.imt.musically.singer.SingerRepository;
import fr.imt.musically.song.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(SingerRepository repository) {
        log.info("Initializing database");
        return args -> {
            for(int i = 0; i < 100; i++) {
                repository.save(new Singer("firstName" + i, "lastName" + i));
            }

            List<Singer> singers = repository.findAll();

            Random random = new Random();

            // Add 50 songs and each song have a random singer
            for (int i = 0; i < 50; i++) {
                // Title, year, rating, singers
                // List of random singers
                List<Singer> singerRandom = new ArrayList<>();
                for (int j = 0; j < random.nextInt(5) + 1; j++) {
                    singerRandom.add(singers.get(random.nextInt(singers.size())));
                }
                Song song = new Song("song" + i, 2000 + i, random.nextDouble() * 5, singerRandom);
            }

        };
    }
}
