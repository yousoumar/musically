package fr.imt.musically.singer;

import fr.imt.musically.song.Song;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "singers")
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany
    @JoinTable(
        name = "singer_song",
        joinColumns = @JoinColumn(name = "singer_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();

    public Singer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected Singer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Song> getSongs() {
        return songs;
    }


    public void addSong(Song song){
        this.songs.add(song);
        song.getSingers().add(this);
    }

    public void removeSong(Song song){
        this.songs.remove(song);
        song.getSingers().remove(this);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
