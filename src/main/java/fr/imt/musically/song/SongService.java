package fr.imt.musically.song;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository repository) {
        this.songRepository = repository;
    }


    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }



}
