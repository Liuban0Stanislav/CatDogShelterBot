package pro.sky.telegrambot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.constants.DocType;
import pro.sky.telegrambot.model.AdoptionDoc;
import pro.sky.telegrambot.service.AdoptionDocService;

@RestController
@RequestMapping("pet-shelter/doc")
public class AdoptionDocController {
    private final AdoptionDocService adoptionDocService;

    public AdoptionDocController(AdoptionDocService adoptionDocService) {
        this.adoptionDocService = adoptionDocService;
    }


    @Operation(
            summary = "Поиск документа для взятия собаки по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден документ с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionDoc.class)
                            )
                    )
            },
            tags = "Работа с документами"
    )
    @GetMapping("{id}")
    public AdoptionDoc readAdoptionDocById(@Parameter(description = "id документа") @PathVariable DocType id) {
        return adoptionDocService.readAdoptionDoc(id);
    }

    @Operation(
            summary = "Добавление нового документа",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый документ с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionDoc.class)
                            )
                    )
            },
            tags = "Работа с документами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового документа",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdoptionDoc.class)
                    )
            )
    )
    @PostMapping
    public AdoptionDoc createAdoptionDoc(@RequestBody AdoptionDoc adoptionDoc) {
        return adoptionDocService.createAdoptionDoc(adoptionDoc);
    }

    @Operation(
            summary = "Редактирование документа",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный документ с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionDoc.class)
                            )
                    )
            },
            tags = "Работа с документами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры документа",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdoptionDoc.class)
                    )
            )
    )
    @PutMapping
    public AdoptionDoc updateAdoptionDoc(@RequestBody AdoptionDoc adoptionDoc) {
        return adoptionDocService.updateAdoptionDoc(adoptionDoc);
    }
}
