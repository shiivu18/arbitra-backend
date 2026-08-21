package com.arbitra.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispute_audits")
public class DisputeAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dispute_id", nullable = false, unique = true)
    private Long disputeId;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "ai_response", columnDefinition = "TEXT")
    private String aiResponse;

    @Column(name = "status", nullable = false)
    private String status = "UNDER_REVIEW";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "UNDER_REVIEW";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDisputeId() { return disputeId; }
    public void setDisputeId(Long disputeId) { this.disputeId = disputeId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getAiResponse() { return aiResponse; }
    public void setAiResponse(String aiResponse) { this.aiResponse = aiResponse; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}