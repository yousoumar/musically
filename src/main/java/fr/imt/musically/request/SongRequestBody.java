package fr.imt.musically.request;

import jakarta.validation.constraints.*;

public class SongRequestBody implements RequestBody {

    @NotBlank
    @Size(min = 2, max = 255)
    private final String title;

    @NotNull
    @Min(1900)
    @Max(2023)
    private final Integer year;

    @NotNull
    @Min(0)
    @Max(5)
    private final Double rating;

    public SongRequestBody(String title, Integer year, Double rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

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
