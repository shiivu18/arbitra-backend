package com.arbitra.backend.repository;

import com.arbitra.backend.model.DisputeEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisputeEvidenceRepository extends JpaRepository<DisputeEvidence, Long> {
    Optional<DisputeEvidence> findByDisputeId(Long disputeId);
}