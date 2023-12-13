package fr.imt.musically;

import fr.imt.musically.singer.SingerRepository;
import fr.imt.musically.singer.Singer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MusicallyController {
    @GetMapping("/")
    String home() {
        return "Hello World !";
    }
}
