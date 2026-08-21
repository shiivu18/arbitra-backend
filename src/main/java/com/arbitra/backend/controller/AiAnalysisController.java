package com.arbitra.backend.controller;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.repository.DisputeRepository;
import com.arbitra.backend.service.AiAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ai")
public class AiAnalysisController {

    @Autowired
    private AiAnalysisService aiAnalysisService;

    @Autowired
    private DisputeRepository disputeRepository;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDispute(@RequestParam Long disputeId) {
        String reason = "General payment chargeback dispute - unauthorized or fraudulent claim";
        
        Optional<Dispute> disputeOpt = disputeRepository.findById(disputeId);
        if (disputeOpt.isPresent()) {
            Dispute dispute = disputeOpt.get();
            // Fallback safely to toString or use generic description if getter varies
            reason = "Dispute ID: " + dispute.getId() + ", Status: " + dispute.getStatus();
        }

        String analysisResult = aiAnalysisService.analyzeDispute(disputeId, reason);
        return ResponseEntity.ok(analysisResult);
    }
}