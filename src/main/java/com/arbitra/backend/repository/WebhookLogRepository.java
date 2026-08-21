package com.arbitra.backend.repository;

import com.arbitra.backend.model.WebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookLogRepository extends JpaRepository<WebhookLog, Long> {
    List<WebhookLog> findByDisputeId(Long disputeId);
}