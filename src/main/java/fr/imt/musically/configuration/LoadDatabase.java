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
            singerRepository.save(new Singer("Nancy", "Ajram"));
            singerRepository.save(new Singer("Taylor", "Swift"));
            singerRepository.save(new Singer("Adele", "Adkins"));
            singerRepository.save(new Singer("Ed", "Sheeran"));
            singerRepository.save(new Singer("Eminem", "Mathers"));

            songRepository.save(new Song("Ya Tabtab Wa Dalla", 2001, 4.5, singerRepository.findByFirstNameAndLastName("Nancy", "Ajram")));
            songRepository.save(new Song("Akhasmak Ah", 2003, 4.5, singerRepository.findByFirstNameAndLastName("Nancy", "Ajram")));
            songRepository.save(new Song("Ah W Noss", 2004, 4.5, singerRepository.findByFirstNameAndLastName("Nancy", "Ajram")));

            songRepository.save(new Song("Love Story", 2008, 4.5, singerRepository.findByFirstNameAndLastName("Taylor", "Swift")));
            songRepository.save(new Song("You Belong With Me", 2008, 4.5, singerRepository.findByFirstNameAndLastName("Taylor", "Swift")));
            songRepository.save(new Song("Blank Space", 2014, 4.5, singerRepository.findByFirstNameAndLastName("Taylor", "Swift")));

            songRepository.save(new Song("Hello", 2015, 4.5, singerRepository.findByFirstNameAndLastName("Adele", "Adkins")));
            songRepository.save(new Song("Someone Like You", 2011, 4.5, singerRepository.findByFirstNameAndLastName("Adele", "Adkins")));
            songRepository.save(new Song("Rolling in the Deep", 2010, 4.5, singerRepository.findByFirstNameAndLastName("Adele", "Adkins")));

            songRepository.save(new Song("Shape of You", 2017, 4.5, singerRepository.findByFirstNameAndLastName("Ed", "Sheeran")));
            songRepository.save(new Song("Thinking Out Loud", 2014, 4.5, singerRepository.findByFirstNameAndLastName("Ed", "Sheeran")));
            songRepository.save(new Song("Perfect", 2017, 4.5, singerRepository.findByFirstNameAndLastName("Ed", "Sheeran")));

            songRepository.save(new Song("Lose Yourself", 2002, 4.5, singerRepository.findByFirstNameAndLastName("Eminem", "Mathers")));
            songRepository.save(new Song("Love The Way You Lie", 2010, 4.5, singerRepository.findByFirstNameAndLastName("Eminem", "Mathers")));
            songRepository.save(new Song("Not Afraid", 2010, 4.5, singerRepository.findByFirstNameAndLastName("Eminem", "Mathers")));
        };
    }

}

