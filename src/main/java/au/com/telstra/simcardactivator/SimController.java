package au.com.telstra.simcardactivator.simcardactivator;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sim")
public class SimController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimRequest simRequest) {
        // Prepare payload for actuator
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", simRequest.getIccid());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        // Call actuator
        ResponseEntity<ActuatorResponse> response =
                restTemplate.postForEntity(ACTUATOR_URL, request, ActuatorResponse.class);

        boolean success = response.getBody() != null && response.getBody().isSuccess();
        System.out.println("Activation success: " + success);

        return ResponseEntity.ok("Activation " + (success ? "successful" : "failed"));
    }

    // --- DTO Classes ---
    public static class SimRequest {
        private String iccid;
        private String customerEmail;

        public String getIccid() { return iccid; }
        public void setIccid(String iccid) { this.iccid = iccid; }
        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    }

    public static class ActuatorResponse {
        private boolean success;

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }
}
