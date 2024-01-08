package fr.imt.musically.singer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import fr.imt.musically.ViewApplication;
import fr.imt.musically.song.Song;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "singers")
public class Singer {

    @JsonView(ViewApplication.SingerWithoutSongsView.class)
    private final UUID singerId = UUID.randomUUID();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @JsonView(ViewApplication.SingerWithoutSongsView.class)
    private String firstName;

    @JsonView(ViewApplication.SingerWithoutSongsView.class)
    private String lastName;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "singer_song",
        joinColumns = @JoinColumn(name = "singer_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @JsonIgnoreProperties("singers")
    private Set<Song> songs = new HashSet<>();

    public Singer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected Singer() {
    }

    public Long getId() {
        return id;
    }

    public UUID getSingerId() {
        return singerId;
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

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs){
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
        song.getSingers().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getSingers().remove(this);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
