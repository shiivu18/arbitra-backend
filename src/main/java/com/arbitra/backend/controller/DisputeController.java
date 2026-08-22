package com.arbitra.backend.controller;

import com.arbitra.backend.event.DisputeStatusChangeEvent;
import com.arbitra.backend.model.DisputeAudit;
import com.arbitra.backend.model.DisputeHistory;
import com.arbitra.backend.repository.DisputeAuditRepository;
import com.arbitra.backend.repository.DisputeHistoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disputes")
@Tag(name = "Dispute Management", description = "Endpoints for managing financial disputes, audits, and status workflows")
public class DisputeController {

    @Autowired
    private DisputeAuditRepository disputeAuditRepository;

    @Autowired
    private DisputeHistoryRepository disputeHistoryRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    @Operation(summary = "Get all disputes", description = "Retrieves a list of all logged dispute audits.")
    public ResponseEntity<List<DisputeAudit>> getAllDisputes() {
        return ResponseEntity.ok(disputeAuditRepository.findAll());
    }

    @GetMapping("/{disputeId}")
    @Operation(summary = "Get dispute by ID", description = "Retrieves a specific dispute audit by its unique dispute ID.")
    public ResponseEntity<DisputeAudit> getDisputeById(@PathVariable Long disputeId) {
        Optional<DisputeAudit> audit = disputeAuditRepository.findByDisputeId(disputeId);
        return audit.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{disputeId}/history")
    @Operation(summary = "Get dispute status history", description = "Retrieves the immutable audit trail of state transitions for a dispute.")
    public ResponseEntity<List<DisputeHistory>> getDisputeHistory(@PathVariable Long disputeId) {
        List<DisputeHistory> history = disputeHistoryRepository.findByDisputeId(disputeId);
        return ResponseEntity.ok(history);


        
    }

    

    @PatchMapping("/{disputeId}/status")
    @Transactional
    @Operation(summary = "Update dispute status", description = "Updates a dispute's status, records history, and fires an async event.")
    public ResponseEntity<DisputeAudit> updateDisputeStatus(
            @PathVariable Long disputeId, 
            @RequestParam String status) {
        Optional<DisputeAudit> auditOpt = disputeAuditRepository.findByDisputeId(disputeId);
        if (auditOpt.isPresent()) {
            DisputeAudit audit = auditOpt.get();
            String oldStatus = audit.getStatus();
            String newStatus = status.toUpperCase();

            if (!oldStatus.equals(newStatus)) {
                audit.setStatus(newStatus);
                disputeAuditRepository.save(audit);

                DisputeHistory history = new DisputeHistory();
                history.setDisputeId(disputeId);
                history.setPreviousStatus(oldStatus);
                history.setNewStatus(newStatus);
                disputeHistoryRepository.save(history);

                eventPublisher.publishEvent(new DisputeStatusChangeEvent(disputeId, oldStatus, newStatus));
            }

            return ResponseEntity.ok(audit);
        }
        return ResponseEntity.notFound().build();
    }
}