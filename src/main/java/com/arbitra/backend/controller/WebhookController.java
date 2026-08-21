package com.arbitra.backend.controller;

import com.arbitra.backend.model.WebhookLog;
import com.arbitra.backend.model.WebhookPayload;
import com.arbitra.backend.repository.WebhookLogRepository;
import com.arbitra.backend.service.AiAnalysisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    @Autowired
    private AiAnalysisService aiAnalysisService;

    @Autowired
    private WebhookLogRepository webhookLogRepository;

    @PostMapping("/simulate-chargeback")
    public ResponseEntity<String> handleIncomingWebhook(@Valid @RequestBody WebhookPayload payload) {
        System.out.println("🔔 Webhook Received: " + payload.getEventType() + " for Dispute ID: " + payload.getDisputeId());

        // 1. Persist the raw webhook payload into PostgreSQL for complete auditability
        WebhookLog log = new WebhookLog();
        log.setDisputeId(payload.getDisputeId());
        log.setEventType(payload.getEventType());
        log.setReason(payload.getReason());
        log.setAmount(payload.getAmount());
        webhookLogRepository.save(log);

        // 2. Trigger automated AI analysis & persistence
        String aiResponse = aiAnalysisService.analyzeDispute(
            payload.getDisputeId(), 
            payload.getReason() != null ? payload.getReason() : "Fraudulent transaction claim"
        );

        return ResponseEntity.ok("Webhook successfully logged and processed. Automated AI Analysis triggered.\n" + aiResponse);
    }
}