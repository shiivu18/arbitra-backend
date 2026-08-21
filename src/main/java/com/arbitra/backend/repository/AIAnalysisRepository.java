package com.arbitra.backend.repository;

import com.arbitra.backend.model.AIAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIAnalysisRepository extends JpaRepository<AIAnalysis, Long> {
    Optional<AIAnalysis> findByDisputeId(Long disputeId);
}