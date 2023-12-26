package fr.imt.musically.singer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/singers")
public class SingerController {
    private final SingerService service;

    public SingerController(SingerService service) {
        this.service = service;
    }

    @GetMapping
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
    public ResponseEntity<List<Singer>> getAllSingers() {
        return ResponseEntity.ok(service.getAllSingers());
    }
}
