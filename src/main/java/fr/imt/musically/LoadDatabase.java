package fr.imt.musically;



import fr.imt.musically.singer.SingerRepository;
import fr.imt.musically.singer.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(SingerRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Singer("Nancy", "Ajram")));
            log.info("Preloading " + repository.save(new Singer("Taylor", "Swift")));
        };
    }
}
