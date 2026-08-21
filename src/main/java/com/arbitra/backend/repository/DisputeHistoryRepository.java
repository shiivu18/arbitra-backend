package com.arbitra.backend.repository;

import com.arbitra.backend.model.DisputeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisputeHistoryRepository extends JpaRepository<DisputeHistory, Long> {
    List<DisputeHistory> findByDisputeId(Long disputeId);
}