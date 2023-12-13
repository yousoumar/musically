package fr.imt.musically.singer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/singers")
public class SingerController {
    private final SingerRepository repository;

    SingerController(SingerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Singer> getAllSinger() {
        return this.repository.findAll();
    }
}
