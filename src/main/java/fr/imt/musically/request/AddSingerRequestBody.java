package fr.imt.musically.request;

import jakarta.validation.constraints.*;

public class AddSingerRequestBody implements RequestBody {

    @NotNull
    @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}$", message = "Singer id must be a valid UUID")
    private String singerId;

    public String getSingerId(){
        return this.singerId;
    }
}
