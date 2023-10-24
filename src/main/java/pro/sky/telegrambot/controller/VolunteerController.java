package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

@RestController
@RequestMapping("pet-shelter/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Поиск волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден волонтер с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с волонтерами"
    )
    @GetMapping("{volunteerId}")
    public ResponseEntity<Volunteer> readVolunteer(@PathVariable long volunteerId) {
        Volunteer findVolunteer = volunteerService.read(volunteerId);
        if (findVolunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findVolunteer);
    }

    @Operation(
            summary = "Добавление нового волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый волонтер с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с волонтерами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PostMapping
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            summary = "Редактирование волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный волонтер с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с волонтерами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PutMapping("{volunteerId}")
    public Volunteer updateVolunteer(@PathVariable long volunteerId,
                                   @RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteerId, volunteer);
    }
}
