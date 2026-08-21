package com.arbitra.backend.event;

public class DisputeStatusChangeEvent {
    private Long disputeId;
    private String previousStatus;
    private String newStatus;

    public DisputeStatusChangeEvent(Long disputeId, String previousStatus, String newStatus) {
        this.disputeId = disputeId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
    }

    public Long getDisputeId() { return disputeId; }
    public String getPreviousStatus() { return previousStatus; }
    public String getNewStatus() { return newStatus; }
}