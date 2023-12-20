package fr.imt.musically.song;

import org.springframework.stereotype.Service;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository repository){
        this.songRepository = repository;
    }


}
