package fr.imt.musically.singer;

import fr.imt.musically.song.Song;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get all singers",
        description = "Get all singers from the database",
        tags = {"singers"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Found all singers",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                                [
                                    {
                                        "id": 1,
                                        "firstName": "Nancy",
                                        "lastName": "Ajram"
                                    },
                                    {
                                        "id": 2,
                                        "firstName": "Taylor",
                                        "lastName": "Swift"
                                    }
                                ]
                            """
                    )
                )
            )
        }
    )
    public ResponseEntity<List<Singer>> getAllSingers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        return ResponseEntity.ok(service.getAllSingers(firstName, lastName));
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
    public ResponseEntity<Singer> createSinger(@Valid @RequestBody SingerBodyRequest singer) {
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
    public ResponseEntity<Object> deleteSinger(@Valid @RequestBody SingerBodyRequest singer) {
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
    public ResponseEntity<Song> updateSinger(@PathVariable("singer_id") String singerId, @PathVariable("song_id") String songId, @PathVariable("rating") String rating) {

        return ResponseEntity.ok(service.updateSongRatingOfASinger(singerId, songId, Double.parseDouble(rating)));
    }
}
