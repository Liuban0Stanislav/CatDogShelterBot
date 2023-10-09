package pro.sky.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.services.AdopterService;

@RestController
@RequestMapping("/adopter")
@Slf4j
public class AdopterController {
    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    /**Метод для создания усыновителей через web интерфейс
     Params= Json Обьект Adopter*/
    @PostMapping
    public ResponseEntity<Adopter> createAdopter(@RequestBody Adopter adopter) {
        log.info("Вызван метод AdopterController.createAdopter , получен Adopter ={}", adopter);
        return ResponseEntity.ok(adopterService.createAdopter(adopter));
    }

    @PutMapping
    public ResponseEntity<Adopter> editAdopter(@RequestBody Adopter adopter) {
        Adopter foundAdopter = adopterService.editAdopter(adopter);
        if (foundAdopter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(adopter);
    }

    @DeleteMapping("{id}")
    public void deleteAdopter(@PathVariable Long id) {
        adopterService.deleteAdopter(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Adopter> getAdopter(@PathVariable Long id) {
        Adopter adopter = adopterService.findAdopter(id);
        if (adopter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopter);
    }
}
