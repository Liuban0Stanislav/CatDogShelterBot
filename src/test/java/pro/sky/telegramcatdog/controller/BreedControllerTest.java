package pro.sky.telegramcatdog.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.telegramcatdog.constants.PetType;
import pro.sky.telegramcatdog.model.Breed;


import static pro.sky.telegramcatdog.constants.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BreedControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void getBreed() {
        // Create new breed and check that it was created OK
        Breed breed = new Breed(1, PetType.DOG, "Русский спаниэль");
        ResponseEntity<Breed> responseCreated = getCreateBreedResponse(breed);
        assertCreatedBreed(breed, responseCreated);

        // Try to get the created breed by its id.
        Breed createdBreed = responseCreated.getBody();
        ResponseEntity<Breed> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + BREED_URL + '/' + createdBreed.getId(),
                Breed.class);

        // Check that the created and selected by id breeds are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdBreed);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createBreedTest() {
        Breed breed = new Breed(1, PetType.DOG, "Русский спаниэль");
        ResponseEntity<Breed> response = getCreateBreedResponse(breed);
        assertCreatedBreed(breed, response);
    }

    @Test
    public void editBreedTest() {
        String oldName = "Русский спаниэль";
        PetType oldPetType = PetType.DOG;
        String newName = "Мей-кун";
        PetType newPetType = PetType.CAT;

        // Create new breed first and check that it was created OK
        Breed breed = new Breed(1, oldPetType, oldName);
        ResponseEntity<Breed> responseCreated = getCreateBreedResponse(breed);
        assertCreatedBreed(breed, responseCreated);

        // Modify the created breed
        Breed createdBreed = responseCreated.getBody();
        createdBreed.setName(newName);
        createdBreed.setPetType(newPetType);

        // Update the modified breed in db
        restTemplate.put(
                LOCALHOST_URL + port + BREED_URL,
                createdBreed);

        // Try to get the updated breed by its id.
        ResponseEntity<Breed> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + BREED_URL + '/' + createdBreed.getId(),
                Breed.class);

        // Check that the updated breed has the same newName, newPetType.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newName);
        Assertions.assertThat(response.getBody().getPetType()).isEqualTo(newPetType);
    }


    private ResponseEntity<Breed> getCreateBreedResponse(Breed breed) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + BREED_URL,
                breed,
                Breed.class);
    }

    private void assertCreatedBreed(Breed breed, ResponseEntity<Breed> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(breed.getId());
    }
}