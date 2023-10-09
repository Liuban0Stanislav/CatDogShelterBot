package pro.sky.telegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.repository.ClientRepository;

@Service
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        log.info("Creating client: {}", client);
        return clientRepository.save(client);
    }

    public Client findClient(Long id) {
        if (clientRepository.findById(id).isPresent()) {
            var findedClient = clientRepository.findById(id).get();
            log.info("Client: {} found", findedClient);
            return findedClient;
        } else {
            throw new RuntimeException();
        }
    }

    public Client editClient(Client client) {
        log.info("The method editClient has invoked");
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        log.info("The method deleteClient has invoked");
        clientRepository.deleteById(id);
    }

}
