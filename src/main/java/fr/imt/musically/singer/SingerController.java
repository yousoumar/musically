package fr.imt.musically.singer;

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
    public ResponseEntity<List<Singer>> getAllSingers() {
        return ResponseEntity.ok(service.getAllSingers());
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
    public ResponseEntity<Object> createSinger(@Valid @RequestBody SingerBodyRequest singer) {
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

    // GET api/singers/?firstName=Nancy&lastName=Ajram (return all singers with the first name Nancy and the last name Ajram)
    // GET api/singers/?firstName=Nancy (return all singers with the first name Nancy)
    // GET api/singers/?lastName=Ajram (return all singers with the last name Ajram)
    // Delete api/singers/{id} (delete the singer with the id {id})
    // Put api/singers/firstName/lastName/rating (update the rating of the singer with the first name firstName and the last name lastName)
}
