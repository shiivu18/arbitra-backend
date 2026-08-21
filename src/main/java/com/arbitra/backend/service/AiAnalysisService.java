package com.arbitra.backend.service;

import com.arbitra.backend.model.AIAnalysis;
import com.arbitra.backend.repository.AIAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AiAnalysisService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private AIAnalysisRepository aiAnalysisRepository;

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=";

    @Transactional
    public String analyzeDispute(Long disputeId, String reason) {
        // Robustness Check: Return cached persisted analysis if already processed
        Optional<AIAnalysis> cachedAnalysis = aiAnalysisRepository.findByDisputeId(disputeId);
        if (cachedAnalysis.isPresent()) {
            return cachedAnalysis.get().getAnalysisContent();
        }

        if (geminiApiKey == null || geminiApiKey.equals("YOUR_ACTUAL_GEMINI_API_KEY")) {
            return "{\"analysis\": \"Gemini API key not configured. Please set a valid key in application.properties.\"}";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = GEMINI_URL + geminiApiKey;

        Map<String, Object> part = new HashMap<>();
        part.put("text", "Analyze this payment chargeback dispute. Dispute ID: " + disputeId + ", Reason: " + reason + ". Provide win probability score (0-100) and recommended defense strategy.");

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            String rawJson = response.getBody();

            // Persist the result securely in PostgreSQL
            AIAnalysis analysisEntity = new AIAnalysis();
            analysisEntity.setDisputeId(disputeId);
            analysisEntity.setAnalysisContent(rawJson);
            analysisEntity.setWinProbability(70); // Default baseline probability
            aiAnalysisRepository.save(analysisEntity);

            return rawJson;
        } catch (Exception e) {
            return "{\"error\": \"Failed to connect to Gemini API: " + e.getMessage() + "\"}";
        }
    }
}