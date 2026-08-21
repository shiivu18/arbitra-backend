package com.arbitra.backend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WebhookPayload {

    @NotNull(message = "Dispute ID cannot be null")
    private Long disputeId;

    @NotBlank(message = "Event type cannot be blank")
    private String eventType; // e.g., "DISPUTE.CREATED"

    private String reason;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Double amount;

    // Getters and Setters
    public Long getDisputeId() { return disputeId; }
    public void setDisputeId(Long disputeId) { this.disputeId = disputeId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}