package com.arbitra.backend.service;

import com.arbitra.backend.model.AIAnalysis;
import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.repository.AIAnalysisRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AiAnalysisService {

    private final AIAnalysisRepository aiAnalysisRepository;

    public AiAnalysisService(AIAnalysisRepository aiAnalysisRepository) {
        this.aiAnalysisRepository = aiAnalysisRepository;
    }

    public AIAnalysis analyzeDispute(Dispute dispute) {
        int winProbabilityScore = 75; 

        AIAnalysis analysis = new AIAnalysis();
        analysis.setDisputeId(dispute.getId());
        analysis.setWinProbability(winProbabilityScore);
        analysis.setCreatedAt(LocalDateTime.now());

        return aiAnalysisRepository.save(analysis);
    }
}