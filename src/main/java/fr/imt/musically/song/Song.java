package fr.imt.musically.song;

import fr.imt.musically.singer.Singer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer year;

    private Double rating;

    @ManyToMany
    @JoinTable(
            name = "singer_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    private List<Singer> singers;

    protected Song() {}
    public Song(String title, Integer year, Double rating, List<Singer> singers) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.singers = singers;

    }

    public Song(String title, Integer year, Double rating, Singer... singers) {
        this(title, year, rating, new ArrayList<>(List.of(singers)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Singer> getSingers() {
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
