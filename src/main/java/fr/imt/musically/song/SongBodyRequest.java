package fr.imt.musically.song;

import jakarta.validation.constraints.*;

public class SongBodyRequest {

    @NotBlank
    @Size(min = 2, max = 255)
    private String title;

    @NotNull
    @Min(1900)
    @Max(2023)
    private Integer year;

    @NotNull
    @Min(0)
    @Max(5)
    private Double rating;

    public String getTitle() {
        return this.title;
    }

    public Integer getYear() {
        return this.year;
    }

    public Double getRating() {
        return this.rating;
    }

}
