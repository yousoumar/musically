package fr.imt.musically.singer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerService {
    private final SingerRepository repository;

    public SingerService(SingerRepository repository) {
        this.repository = repository;

    }

    public List<Singer> getAllSingers() {
        return repository.findAll();
    }
}
