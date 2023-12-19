package fr.imt.musically;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MusicallyController {
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
