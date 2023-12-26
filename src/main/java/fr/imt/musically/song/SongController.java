package fr.imt.musically.song;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
