package com.arbitra.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispute_evidence")
public class DisputeEvidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dispute_id", nullable = false)
    private Long disputeId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String requiredEvidence;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String defenseLetter;

    @Column(nullable = false)
    private String status; // e.g., "GENERATED", "SUBMITTED"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "GENERATED";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDisputeId() { return disputeId; }
    public void setDisputeId(Long disputeId) { this.disputeId = disputeId; }

    public String getRequiredEvidence() { return requiredEvidence; }
    public void setRequiredEvidence(String requiredEvidence) { this.requiredEvidence = requiredEvidence; }

    public String getDefenseLetter() { return defenseLetter; }
    public void setDefenseLetter(String defenseLetter) { this.defenseLetter = defenseLetter; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}