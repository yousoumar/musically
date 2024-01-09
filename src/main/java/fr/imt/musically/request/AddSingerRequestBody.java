package fr.imt.musically.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddSingerRequestBody implements RequestBody {

    @NotNull
    @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}$", message = "Singer id must be a valid UUID")
    private String singerId;

    public AddSingerRequestBody(String singerId) {
        this.singerId = singerId;
    }

    public AddSingerRequestBody(){
    }

    public String getSingerId() {
        return this.singerId;
    }
}
