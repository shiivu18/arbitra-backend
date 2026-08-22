package com.arbitra.backend.controller;

import com.arbitra.backend.model.WebhookLog;
import com.arbitra.backend.repository.WebhookLogRepository;
import com.arbitra.backend.service.WebhookVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private final WebhookVerificationService verificationService;
    private final WebhookLogRepository webhookLogRepository;

    public WebhookController(WebhookVerificationService verificationService, WebhookLogRepository webhookLogRepository) {
        this.verificationService = verificationService;
        this.webhookLogRepository = webhookLogRepository;
    }

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(
            @RequestHeader("X-Razorpay-Signature") String signature,
            @RequestBody String rawPayload) {

        // 1. Verify Signature
        boolean isValid = verificationService.verifySignature(rawPayload, signature);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Signature");
        }

        // 2. Persist Webhook Log Entry
        WebhookLog log = new WebhookLog();
        webhookLogRepository.save(log);

        System.out.println(">> Verified Razorpay Webhook logged to database successfully!");

        return ResponseEntity.ok("Webhook processed and logged successfully");
    }
}