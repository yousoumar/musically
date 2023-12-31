package fr.imt.musically.singer;

import com.fasterxml.jackson.annotation.JsonView;
import fr.imt.musically.ViewApplication;
import fr.imt.musically.request.SingerRequestBody;
import fr.imt.musically.song.Song;
import fr.imt.musically.request.SongRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/singers")
public class SingerController {
    private final SingerService service;

    public SingerController(SingerService service) {
        this.service = service;
    }

    @JsonView(ViewApplication.SingerWithoutSongsView.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get all singers",
        description = "Get all singers from the database",
        tags = {"singers"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Found all singers"
            )
        }
    )
    public ResponseEntity<List<Singer>> getAllSingers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        return ResponseEntity.ok(service.getAllSingers(firstName, lastName));
    }


    @GetMapping(path = "/{singer_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get a singer with its songs",
        description = "Get a singer with its songs",
        tags = {"singers"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a singer with its songs"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content
            )
        }
    )
    public ResponseEntity<Singer> getOneSinger(@PathVariable("singer_id") @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String singerId) {
        return ResponseEntity.ok(service.getSinger(singerId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Create a singer",
        description = "Create a singer in the database",
        tags = {"singers"},

        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Created a singer"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content
            )
        }
    )
    public ResponseEntity<Singer> createSinger(@Valid @RequestBody SingerRequestBody singer) {
        return ResponseEntity.ok(service.createSinger(singer));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Delete a singer",
        description = "Delete a singer in the database",
        tags = {"singers"},

        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Deleted a singer"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content
            )
        }
    )
    public ResponseEntity<Object> deleteSinger(@Valid @RequestBody SingerRequestBody singer) {
        service.deleteSinger(singer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{singer_id}/{song_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Update a singer",
        description = "Update a singer in the database",
        tags = {"singers"},

        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Updated a singer"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content
            )
        }
    )
    public ResponseEntity<Song> updateSinger(
        @PathVariable("singer_id") @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String singerId,
        @PathVariable("song_id") @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String songId,
        @PathVariable("rating") @Pattern(regexp = "[0-5](\\.[0-9]+)?") String rating
    ) {
        return ResponseEntity.ok(service.updateSongRatingOfASinger(singerId, songId, Double.parseDouble(rating)));
    }

    @PutMapping(path = "/{singer_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Add songs to a singer",
        description = "Update a singer in the database by adding transacyional songs to it",
        tags = {"singers"},

        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Updated a singer"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content
            )
        }
    )
    public ResponseEntity<Singer> addSongs(
            @PathVariable("singer_id")
            @Pattern(
                regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}"
            )
            String singerId,
            @Valid @RequestBody SongRequestBody... songBody
    ) {
        return ResponseEntity.ok(service.addSongs(singerId, songBody));
    }
}
