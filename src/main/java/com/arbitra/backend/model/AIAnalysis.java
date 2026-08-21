package com.arbitra.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_analysis")
public class AIAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dispute_id", nullable = false)
    private Long disputeId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String analysisContent;

    @Column(name = "win_probability")
    private Integer winProbability;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDisputeId() { return disputeId; }
    public void setDisputeId(Long disputeId) { this.disputeId = disputeId; }

    public String getAnalysisContent() { return analysisContent; }
    public void setAnalysisContent(String analysisContent) { this.analysisContent = analysisContent; }

    public Integer getWinProbability() { return winProbability; }
    public void setWinProbability(Integer winProbability) { this.winProbability = winProbability; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}