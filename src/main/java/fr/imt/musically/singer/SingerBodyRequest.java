package fr.imt.musically.singer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SingerBodyRequest {

    @NotBlank
    @Size(min = 2, max = 255)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 255)
    private String lastName;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
