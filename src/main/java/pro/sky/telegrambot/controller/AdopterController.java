package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.service.AdopterService;

@RestController
@RequestMapping("pet-shelter/adopter")
public class AdopterController {
    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(
            summary = "Поиск усыновителя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден усыновитель с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter.class)
                            )
                    )
            },
            tags = "Работа с усыновителями"
    )
    @GetMapping("{id}")
    public Adopter readAdopterById(@Parameter(description = "id усыновителя", example = "1") @PathVariable Long id) {
        return adopterService.readAdopter(id);
    }

    @Operation(
            summary = "Добавление нового усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый усыновитель с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter.class)
                            )
                    )
            },
            tags = "Работа с усыновителями",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового усыновителя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Adopter.class)
                    )
            )
    )
    @PostMapping
    public Adopter createAdopter(@RequestBody Adopter adopter) {
        return adopterService.createAdopter(adopter);
    }

    @Operation(
            summary = "Редактирование усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный усыновитель с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter.class)
                            )
                    )
            },
            tags = "Работа с усыновителями",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры усыновителя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Adopter.class)
                    )
            )
    )
    @PutMapping
    public Adopter updateAdopter(@RequestBody Adopter adopter) {
        return adopterService.updateAdopter(adopter);
    }
}
