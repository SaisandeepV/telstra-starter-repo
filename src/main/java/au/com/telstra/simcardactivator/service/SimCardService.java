package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.model.SimCardRecord;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimCardService {

    @Autowired
    private SimCardRepository simCardRepository;

    // Save SIM activation details
    public SimCardRecord saveSimCardRecord(SimCardRecord record) {
        return simCardRepository.save(record);
    }

    // Fetch SIM by ICCID (optional, for testing)
    public SimCardRecord getSimCardByIccid(String iccid) {
        return simCardRepository.findByIccid(iccid);
    }

    public SimCardRecord getSimCardRecordById(Long id) {
        return simCardRepository.findById(id).orElse(null);
    }
}
