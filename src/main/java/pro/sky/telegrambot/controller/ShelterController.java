package pro.sky.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.services.ShelterService;

@RestController
@RequestMapping("/shelter")
@Slf4j
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<Shelter> createReport(@RequestBody Shelter shelter) {
        log.info("Вызван метод ShelterController.createShelter , получен Shelter ={}", shelter);
        return ResponseEntity.ok(shelterService.createShelter(shelter));
    }

    @PutMapping
    public ResponseEntity<Shelter> editReport(@RequestBody Shelter shelter) {
        Shelter foundShelter = shelterService.editShelter(shelter);
        if (foundShelter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(shelter);
    }

    @DeleteMapping("{id}")
    public void deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Shelter> getShelter(@PathVariable Long id) {
        Shelter shelter = shelterService.findShelter(id);
        if (shelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelter);
    }
}
