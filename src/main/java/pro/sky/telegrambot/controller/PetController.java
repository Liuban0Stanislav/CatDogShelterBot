package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.service.PetService;

@RestController
@RequestMapping("pet-shelter/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Поиск питомца по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден питомец с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами"
    )
    @GetMapping("{id}")
    public Pet getPet(@Parameter(description = "id питомца", example = "1") @PathVariable Long id) {
        return petService.findPet(id);
    }

    @Operation(
            summary = "Добавление нового питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый питомец с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового питомца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )
    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @Operation(
            summary = "Редактирование питомца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный питомец с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            },
            tags = "Работа с питомцами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры питомца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )
    @PutMapping
    public Pet editPet(@RequestBody Pet pet) {
        return petService.editPet(pet);
    }
}
