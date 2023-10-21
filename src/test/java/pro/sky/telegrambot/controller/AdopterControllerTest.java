package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.telegrambot.model.Adopter;

import static pro.sky.telegrambot.constants.Constants.ADOPTER_URL;
import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AdopterControllerTest {

//    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAdopterTest() {
        Adopter adopter = new Adopter(1, "test", "test", "123", 123, "test", 30);
        ResponseEntity<Adopter> response = getCreateAdopterResponse(adopter);
        assertCreatedAdopter(adopter, response);
    }

    @Test
    public void updateAdopterTest() {
        String oldFirstName = "Denis";
        String newFirstName = "Dionis";

        // Create new adopter first and check that it was created OK
        Adopter adopter = new Adopter(1, oldFirstName, "test", "123", 123, "test", 30);
        ResponseEntity<Adopter> responseCreated = getCreateAdopterResponse(adopter);
        assertCreatedAdopter(adopter, responseCreated);

        // Modify the created adopter
        Adopter createdAdopter = responseCreated.getBody();
        createdAdopter.setFirstName(newFirstName);

        // Update the modified adopter in db
        restTemplate.put(
                LOCALHOST_URL + port + ADOPTER_URL,
                createdAdopter);

        // Try to get the updated adopter by its id.
        ResponseEntity<Adopter> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTER_URL + '/' + createdAdopter.getId(),
                Adopter.class);

        // Check that the updated doc has the same newDesc.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getFirstName()).isEqualTo(newFirstName);
    }

    @Test
    public void readAdopterTest() {
        // Create new adopter and check that it was created OK
        Adopter adopter = new Adopter(1, "test", "test", "123", 123, "test", 30);
        ResponseEntity<Adopter> responseCreated = getCreateAdopterResponse(adopter);
        assertCreatedAdopter(adopter, responseCreated);

        // Try to get the created adopter by its id.
        Adopter createdAdopter = responseCreated.getBody();
        ResponseEntity<Adopter> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTER_URL + '/' + createdAdopter.getId(),
                Adopter.class);

        // Check that the created and selected by id adopters are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdAdopter);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<Adopter> getCreateAdopterResponse(Adopter adopter) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + ADOPTER_URL,
                adopter,
                Adopter.class);
    }

    private void assertCreatedAdopter(Adopter adopter, ResponseEntity<Adopter> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(adopter.getId());
    }
}
