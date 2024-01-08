package fr.imt.musically.song;

import fr.imt.musically.request.AddSingerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    @GetMapping("")
    @Operation(
        summary = "Get all songs",
        description = "Get all songs from the database",
        tags = {"songs"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Found all songs")
        }
    )
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(service.getAllSongs());
    }

    @PutMapping(path = "/{songId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Add singers to a song",
        description = "Add singers to a song",
        tags = {"songs"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Added songs to a singer")
        }
    )
    public ResponseEntity<Song> addSingersToSong(@PathVariable("songId") String songId, @Valid @RequestBody List<AddSingerRequestBody> singerBody) {
        return ResponseEntity.ok(service.addSingers(songId, singerBody));
    }
}
