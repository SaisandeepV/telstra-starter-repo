package stepDefinitions;

import au.com.telstra.simcardactivator.model.SimCardRecord;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String iccid;
    private int recordId;

    @Given("I have an ICCID {string} for record ID {int}")
    public void i_have_an_iccid_for_record_id(String iccid, int recordId) {
        this.iccid = iccid;
        this.recordId = recordId;
    }

    @When("I send a POST request to {string} to activate the SIM")
    public void i_send_a_post_request_to_activate_the_sim(String url) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("iccid", iccid);
        requestBody.put("customerEmail", "test@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        response = restTemplate.postForEntity("http://localhost:8080" + url, request, String.class);
    }

    @Then("the activation response should indicate success")
    public void the_activation_response_should_indicate_success() {
        assertTrue(response.getBody().contains("Activation successful"));
    }

    @Then("the activation response should indicate failure")
    public void the_activation_response_should_indicate_failure() {
        assertFalse(response.getBody().contains("Activation successful"));
    }

    @Then("the SIM card record should have active status {string}")
    public void the_sim_card_record_should_have_active_status(String expectedActive) {
        SimCardRecord record = restTemplate.getForObject(
                "http://localhost:8080/sim/query?simCardId=" + recordId,
                SimCardRecord.class
        );
        boolean expected = Boolean.parseBoolean(expectedActive);
        assertEquals(expected, record.isActive());
    }
}
