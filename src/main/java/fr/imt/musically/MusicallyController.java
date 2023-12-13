package fr.imt.musically;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicallyController {
    @GetMapping("/")
    String home() {
        return "Hello World !";
    }
}
