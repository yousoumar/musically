package fr.imt.musically.song;

import fr.imt.musically.singer.Singer;
import jakarta.persistence.*;

import java.util.List;

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
    private List<Singer> singers;

    protected Song() {}
    public Song(String title, Integer year, Double rating, List<Singer> singers) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.singers = singers;
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

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public void addSinger(Singer singer) {
        this.singers.add(singer);
    }

    public void removeSinger(Singer singer) {
        this.singers.remove(singer);
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
