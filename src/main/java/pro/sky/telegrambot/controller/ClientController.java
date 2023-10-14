package pro.sky.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.services.ClientService;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        log.info("Вызван метод ClientController.createClient , получен Client ={}", client);
        return ResponseEntity.ok(clientService.createClient(client));
    }

    @PutMapping
    public ResponseEntity<Client> editClient(@RequestBody Client client) {
        Client foundClient = clientService.editClient(client);
        if (foundClient == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Client client = clientService.findClient(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }
}
