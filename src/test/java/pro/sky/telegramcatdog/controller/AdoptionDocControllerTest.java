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
import pro.sky.telegramcatdog.model.AdoptionDoc;

import static pro.sky.telegramcatdog.constants.Constants.ADOPTIONDOC_URL;
import static pro.sky.telegramcatdog.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegramcatdog.constants.DocType.INFO_MEETING_DOG;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AdoptionDocControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createDocTest() {
        AdoptionDoc adoptionDoc = new AdoptionDoc(INFO_MEETING_DOG, "long");
        ResponseEntity<AdoptionDoc> response = getCreateAdoptionDocResponse(adoptionDoc);
        assertCreatedAdoptionDoc(adoptionDoc, response);
    }

    @Test
    public void editBranchTest() {
        String oldDesc = "Make fun";
        String newDesc = "Make more fun";

        // Create new doc first and check that it was created OK
        AdoptionDoc adoptionDoc = new AdoptionDoc(INFO_MEETING_DOG, "Something");
        ResponseEntity<AdoptionDoc> responseCreated = getCreateAdoptionDocResponse(adoptionDoc);
        assertCreatedAdoptionDoc(adoptionDoc, responseCreated);

        // Modify the created doc
        AdoptionDoc createdDoc = responseCreated.getBody();
        createdDoc.setDescription(newDesc);

        // Update the modified doc in db
        restTemplate.put(
                LOCALHOST_URL + port + ADOPTIONDOC_URL,
                createdDoc);

        // Try to get the updated doc by its id.
        ResponseEntity<AdoptionDoc> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTIONDOC_URL + '/' + createdDoc.getId(),
                AdoptionDoc.class);

        // Check that the updated doc has the same newDesc.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getDescription()).isEqualTo(newDesc);
    }

    @Test
    public void getDocByIdTest() {
        // Create new doc and check that it was created OK
        AdoptionDoc adoptionDoc = new AdoptionDoc(INFO_MEETING_DOG, "Something");
        ResponseEntity<AdoptionDoc> responseCreated = getCreateAdoptionDocResponse(adoptionDoc);
        assertCreatedAdoptionDoc(adoptionDoc, responseCreated);

        // Try to get the created doc by its id.
        AdoptionDoc createdDoc = responseCreated.getBody();
        ResponseEntity<AdoptionDoc> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTIONDOC_URL + '/' + createdDoc.getId(),
                AdoptionDoc.class);

        // Check that the created and selected by id docs are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdDoc);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<AdoptionDoc> getCreateAdoptionDocResponse(AdoptionDoc adoptionDoc) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + ADOPTIONDOC_URL,
                adoptionDoc,
                AdoptionDoc.class);
    }

    private void assertCreatedAdoptionDoc(AdoptionDoc adoptionDoc, ResponseEntity<AdoptionDoc> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(adoptionDoc.getId());
    }
}