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
import pro.sky.telegrambot.constants.Color;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.constants.Sex;
import pro.sky.telegrambot.model.Pet;

import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegrambot.constants.Constants.PET_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PetControllerTest {

//    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPet() {
        // Create new pet and check that it was created OK
        Pet pet = new Pet(1, "Bim", PetType.DOG, Color.BLACK, Sex.MALE);
        ResponseEntity<Pet> responseCreated = getCreatePetResponse(pet);
        assertCreatedPet(pet, responseCreated);

        // Try to get the created pet by its id.
        Pet createdPet = responseCreated.getBody();
        ResponseEntity<Pet> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_URL + '/' + createdPet.getId(),
                Pet.class);

        // Check that the created and selected by id pets are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdPet);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createPet() {
        Pet pet = new Pet(1, "Bim", PetType.DOG, Color.BLACK, Sex.MALE);
        ResponseEntity<Pet> response = getCreatePetResponse(pet);
        assertCreatedPet(pet, response);
    }

    @Test
    void editPet() {
        String oldNickName = "Bim";
        Color oldColor = Color.BLACK;
        String newNickName = "Sharik";
        Color newColor = Color.WHITE;

        // Create new pet first and check that it was created OK
        Pet pet = new Pet(1, oldNickName, PetType.DOG, oldColor, Sex.MALE);
        ResponseEntity<Pet> responseCreated = getCreatePetResponse(pet);
        assertCreatedPet(pet, responseCreated);

        // Modify the created pet
        Pet createdPet = responseCreated.getBody();
        createdPet.setNickName(newNickName);
        createdPet.setColor(newColor);

        // Update the modified pet in db
        restTemplate.put(
                LOCALHOST_URL + port + PET_URL,
                createdPet);

        // Try to get the updated pet by its id.
        ResponseEntity<Pet> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + PET_URL + '/' + createdPet.getId(),
                Pet.class);

        // Check that the updated pet has the same newNickName, newColor.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getNickName()).isEqualTo(newNickName);
        Assertions.assertThat(response.getBody().getColor()).isEqualTo(newColor);
    }

    private ResponseEntity<Pet> getCreatePetResponse(Pet pet) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + PET_URL,
                pet,
                Pet.class);
    }

    private void assertCreatedPet(Pet pet, ResponseEntity<Pet> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(pet.getId());
    }
}