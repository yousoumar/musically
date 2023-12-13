package fr.imt.musically.singer;

import java.util.List;

public class SingerService {
    private final SingerRepository repository;

    public SingerService(SingerRepository repository){
        this.repository=repository;

    }

    public List<Singer> getAllSingers() {
        return repository.findAll();
    }
}
