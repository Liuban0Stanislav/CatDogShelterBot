package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.AdoptionReport;
import pro.sky.telegrambot.service.AdoptionReportService;

@RestController
@RequestMapping("/pet-shelter/adoptionReport")
public class AdoptionReportController {

    private final AdoptionReportService adoptionReportService;

    public AdoptionReportController(AdoptionReportService adoptionReportService) {
        this.adoptionReportService = adoptionReportService;
    }



    @Operation(
            summary = "Добавление ежедневного отчета ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый ежедневный отчет с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionReport.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового ежедневного отчета",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdoptionReport.class)
                    )
            )
    )
    @PostMapping
    public AdoptionReport createAdoptionReport(@RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.createAdoptionReport(adoptionReport);
    }


    @Operation(
            summary = "Редактирование ежедневного отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный ежедневный отчет с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionReport.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdoptionReport.class)
                    )
            )
    )
    @PutMapping("/{adoptionReportId}")
    public AdoptionReport updateAdoptionReport(@PathVariable long adoptionReportId,
                            @RequestBody AdoptionReport adoptionReport) {
        return adoptionReportService.updateAdoptionReport(adoptionReportId, adoptionReport);
    }



    @Operation(
            summary = "Поиск ежедневного отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден ежедневный отчет с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdoptionReport.class)
                            )
                    )
            },
            tags = "Работа с ежедневными отчетами"
    )
    @GetMapping("{adoptionReportId}")
    public ResponseEntity<AdoptionReport> readAdoptionReport(@PathVariable long adoptionReportId) {
        AdoptionReport findAdoptionReport = adoptionReportService.readAdoptionReport(adoptionReportId);
        if (findAdoptionReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findAdoptionReport);
    }

}
