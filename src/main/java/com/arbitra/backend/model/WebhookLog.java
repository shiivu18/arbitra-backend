package com.arbitra.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_logs")
public class WebhookLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dispute_id", nullable = false)
    private Long disputeId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @PrePersist
    protected void onCreate() {
        this.receivedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDisputeId() { return disputeId; }
    public void setDisputeId(Long disputeId) { this.disputeId = disputeId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}