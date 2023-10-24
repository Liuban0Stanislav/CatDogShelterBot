package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Breed;
import pro.sky.telegrambot.service.BreedService;

@RestController
@RequestMapping("pet-shelter/breed")
public class BreedController {

    private final BreedService breedService;


    public BreedController(BreedService breedService) {
        this.breedService = breedService;
    }

    @Operation(
            summary = "Поиск породы по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдена порода с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Breed.class)
                            )
                    )
            },
            tags = "Работа с породами"
    )
    @GetMapping("{id}")
    public Breed getBreedId(@Parameter(description = "id породы", example = "1") @PathVariable Integer id) {
        return breedService.readBreed(id);
    }

    @Operation(
            summary = "Добавление новой породы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлена новая порода с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Breed.class)
                            )
                    )
            },
            tags = "Работа с породами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры новой породы",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Breed.class)
                    )
            )
    )
    @PostMapping
    public Breed createBreed(@RequestBody Breed breed) {
        return breedService.createBreed(breed);
    }

    @Operation(
            summary = "Редактирование породы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленная порода с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Breed.class)
                            )
                    )
            },
            tags = "Работа с породами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры породы",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Breed.class)
                    )
            )
    )
    @PutMapping
    public Breed editBreed(@RequestBody Breed breed) {
        return breedService.editBreed(breed);
    }
}
