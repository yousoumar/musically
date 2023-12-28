package fr.imt.musically.song;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.imt.musically.singer.Singer;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private final UUID songId = UUID.randomUUID();

    private String title;

    private Integer year;

    private Double rating;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "songs"
    )
    @JsonIgnoreProperties("songs")
    private Set<Singer> singers;

    protected Song() {
    }

    public Song(String title, Integer year, Double rating, Set<Singer> singers) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.singers = singers;

        for (Singer singer : singers) {
            singer.getSongs().add(this);
        }
    }

    public Song(String title, Integer year, Double rating, Singer... singers) {
        this(title, year, rating, new HashSet<>(Set.of(singers)));
    }

    public Long getId() {
        return id;
    }

    public UUID getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<Singer> getSingers() {
        return singers;
    }

    public void addSinger(Singer singer) {
        this.singers.add(singer);
        singer.getSongs().add(this);
    }

    public void removeSinger(Singer singer) {
        this.singers.remove(singer);
        singer.getSongs().remove(this);
    }

    @Override
    public String toString() {
        return "Song{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", year=" + year +
            ", rating=" + rating +
            ", singers=" + singers.stream().map(Singer::getFullName).reduce("", (acc, singer) -> acc + ", " + singer) +
            '}';
    }
}
