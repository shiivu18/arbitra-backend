package com.arbitra.backend.repository;

import com.arbitra.backend.model.DisputeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisputeAuditRepository extends JpaRepository<DisputeAudit, Long> {
    Optional<DisputeAudit> findByDisputeId(Long disputeId);
}