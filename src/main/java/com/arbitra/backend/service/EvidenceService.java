package com.arbitra.backend.service;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.model.DisputeEvidence;
import com.arbitra.backend.repository.DisputeEvidenceRepository;
import com.arbitra.backend.repository.DisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EvidenceService {

    @Autowired
    private DisputeRepository disputeRepository;

    @Autowired
    private DisputeEvidenceRepository disputeEvidenceRepository;

    @Transactional
    public String generateDefensePacket(Long disputeId) {
        // Robust check: Return existing persisted evidence if already generated
        Optional<DisputeEvidence> existingEvidence = disputeEvidenceRepository.findByDisputeId(disputeId);
        if (existingEvidence.isPresent()) {
            DisputeEvidence ev = existingEvidence.get();
            return formatEvidenceJson(ev.getDisputeId(), ev.getStatus(), ev.getRequiredEvidence(), ev.getDefenseLetter());
        }

        Optional<Dispute> disputeOptional = disputeRepository.findById(disputeId);
        if (disputeOptional.isEmpty()) {
            return "{\"error\": \"Dispute not found with ID: " + disputeId + "\"}";
        }

        Dispute dispute = disputeOptional.get();

        String evidenceList = "[\"Customer Signed Invoice / Tax Receipt\", \"Courier Delivery Confirmation & GPS Log\", \"IP Address & Device Fingerprint Log at Checkout\"]";
        String defenseLetter = String.format("Dear Chargeback Team, We strongly contest this dispute for Dispute ID %d. The transaction was fully authorized, verified via 3D Secure, and successfully delivered to the cardholder's verified address with complete delivery logs.", dispute.getId());

        // Persist robustly into PostgreSQL
        DisputeEvidence newEvidence = new DisputeEvidence();
        newEvidence.setDisputeId(dispute.getId());
        newEvidence.setRequiredEvidence(evidenceList);
        newEvidence.setDefenseLetter(defenseLetter);
        newEvidence.setStatus("GENERATED");
        disputeEvidenceRepository.save(newEvidence);

        return formatEvidenceJson(newEvidence.getDisputeId(), newEvidence.getStatus(), evidenceList, defenseLetter);
    }

    private String formatEvidenceJson(Long disputeId, String status, String evidence, String letter) {
        return String.format(
            "{\n" +
            "  \"disputeId\": %d,\n" +
            "  \"status\": \"%s\",\n" +
            "  \"requiredEvidence\": %s,\n" +
            "  \"merchantDefenseLetter\": \"%s\"\n" +
            "}",
            disputeId,
            status,
            evidence,
            letter
        );
    }
}