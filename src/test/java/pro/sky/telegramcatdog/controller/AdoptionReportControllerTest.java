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
import pro.sky.telegramcatdog.model.AdoptionReport;

import java.util.HashMap;
import java.util.Map;

import static pro.sky.telegramcatdog.constants.Constants.ADOPTION_REPORT_URL;
import static pro.sky.telegramcatdog.constants.Constants.LOCALHOST_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdoptionReportControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TelegramBot telegramBot;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAdoptionReportTest() {
        AdoptionReport adoptionReport = new AdoptionReport(1l, null, "1", "1", null);
        ResponseEntity<AdoptionReport> response = getCreateAdoptionReportResponse(adoptionReport);
        assertCreatedAdoptionReport(adoptionReport, response);
    }

    @Test
    void updateAdoptionReportTest() {
        String oldDiet = "1";
        String oldWellBeing = "1";
        String oldBehaviorChange = "1";
        String newDiet = "2";
        String newWellBeing = "2";
        String newBehaviorChange = "2";

        // Create new AdoptionReport first and check that it was created OK
        AdoptionReport adoptionReport = new AdoptionReport(1l, null, oldDiet, oldWellBeing, oldBehaviorChange);
        ResponseEntity<AdoptionReport> responseCreated = getCreateAdoptionReportResponse(adoptionReport);
        assertCreatedAdoptionReport(adoptionReport, responseCreated);

        // Modify the created AdoptionReport
        AdoptionReport createdAdoptionReport = responseCreated.getBody();
        createdAdoptionReport.setDiet(newDiet);
        createdAdoptionReport.setWellBeing(newWellBeing);
        createdAdoptionReport.setBehaviorChange(newBehaviorChange);

        Map< String, String > params = new HashMap< String, String >();
        params.put("id", Long.toString(createdAdoptionReport.getId()));
        restTemplate.put(
                LOCALHOST_URL + port + ADOPTION_REPORT_URL + "/{id}",
                createdAdoptionReport, params);

        // Try to get the updated volunteer by its id.
        ResponseEntity<AdoptionReport> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTION_REPORT_URL + '/' + createdAdoptionReport.getId(),
                AdoptionReport.class);


        // Check that the updated AdoptionReport has the same newName, newChatId.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getDiet()).isEqualTo(newDiet);
        Assertions.assertThat(response.getBody().getWellBeing()).isEqualTo(newWellBeing);
        Assertions.assertThat(response.getBody().getBehaviorChange()).isEqualTo(newBehaviorChange);
    }

    @Test
    void findAdoptionReportTest() {
        // Create new AdoptionReport and check that it was created OK
        AdoptionReport adoptionReport = new AdoptionReport(1l, null, "1", "1", null);
        ResponseEntity<AdoptionReport> responseCreated = getCreateAdoptionReportResponse(adoptionReport);
        assertCreatedAdoptionReport(adoptionReport, responseCreated);

        // Try to get the created volunteer by its id.
        AdoptionReport createdAdoptionReport = responseCreated.getBody();
        ResponseEntity<AdoptionReport> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + ADOPTION_REPORT_URL + '/' + createdAdoptionReport.getId(),
                AdoptionReport.class);

        // Check that the created and selected by id AdoptionReports are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdAdoptionReport);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    private ResponseEntity<AdoptionReport> getCreateAdoptionReportResponse(AdoptionReport adoptionReport) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + ADOPTION_REPORT_URL,
                adoptionReport,
                AdoptionReport.class);
    }
    private void assertCreatedAdoptionReport( AdoptionReport adoptionReport, ResponseEntity<AdoptionReport> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(adoptionReport.getId());
    }

}
