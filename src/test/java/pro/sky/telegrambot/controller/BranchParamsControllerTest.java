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
import pro.sky.telegrambot.model.BranchParams;

import static pro.sky.telegrambot.constants.Constants.BRANCHPARAMS_URL;
import static pro.sky.telegrambot.constants.Constants.LOCALHOST_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BranchParamsControllerTest {

//    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createBranchTest() {
        BranchParams branchParams = new BranchParams(1, "Branch 1");
        ResponseEntity<BranchParams> response = getCreateBranchParamsResponse(branchParams);
        assertCreatedBranchParams(branchParams, response);
    }

    @Test
    public void editBranchTest() {
        String oldName = "Branch 1";
        String newName = "Updated Branch 1";

        // Create new branch first and check that it was created OK
        BranchParams branchParams = new BranchParams(1, oldName);
        ResponseEntity<BranchParams> responseCreated = getCreateBranchParamsResponse(branchParams);
        assertCreatedBranchParams(branchParams, responseCreated);

        // Modify the created branch
        BranchParams createdBranch = responseCreated.getBody();
        createdBranch.setName(newName);

        // Update the modified branch in db
        restTemplate.put(
                LOCALHOST_URL + port + BRANCHPARAMS_URL,
                createdBranch);

        // Try to get the updated branch by its id.
        ResponseEntity<BranchParams> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + BRANCHPARAMS_URL + '/' + createdBranch.getId(),
                BranchParams.class);

        // Check that the updated branch has the same newName.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newName);
    }

    @Test
    public void getBranchByIdTest() {
        // Create new branch and check that it was created OK
        BranchParams branchParams = new BranchParams(1, "Branch 1");
        ResponseEntity<BranchParams> responseCreated = getCreateBranchParamsResponse(branchParams);
        assertCreatedBranchParams(branchParams, responseCreated);

        // Try to get the created branch by its id.
        BranchParams createdBranch = responseCreated.getBody();
        ResponseEntity<BranchParams> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + BRANCHPARAMS_URL + '/' + createdBranch.getId(),
                BranchParams.class);

        // Check that the created and selected by id branches are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdBranch);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<BranchParams> getCreateBranchParamsResponse(BranchParams branchParams) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + BRANCHPARAMS_URL,
                branchParams,
                BranchParams.class);
    }

    private void assertCreatedBranchParams(BranchParams branchParams, ResponseEntity<BranchParams> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(branchParams.getId());
    }
}