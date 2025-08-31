package au.com.telstra.simcardactivator;

import au.com.telstra.simcardactivator.model.SimCardRecord;
import au.com.telstra.simcardactivator.service.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sim")
public class SimController {

    @Autowired
    private SimCardService simCardService;

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody Map<String, String> request) {
        String iccid = request.get("iccid");
        String customerEmail = request.get("customerEmail");

        // ✅ Save record in DB
        SimCardRecord record = new SimCardRecord(iccid, customerEmail, true);
        simCardService.saveSimCardRecord(record);

        return ResponseEntity.ok("Activation successful for ICCID: " + iccid);
    }

    @GetMapping("/query")
    public ResponseEntity<SimCardRecord> getSimById(@RequestParam Long simCardId) {
        SimCardRecord record = simCardService.getSimCardRecordById(simCardId);
        if (record != null) {
            return ResponseEntity.ok(record);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
