package com.arbitra.backend.dto;

import com.arbitra.backend.model.Dispute;
import java.time.OffsetDateTime;

public class DisputeResponse {
    private Long id;
    private String title;
    private String status;
    private OffsetDateTime createdAt;

    public DisputeResponse(Dispute dispute) {
        this.id = dispute.getId();
        this.title = dispute.getTitle();
        this.status = dispute.getStatus();
        this.createdAt = dispute.getCreatedAt();
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}