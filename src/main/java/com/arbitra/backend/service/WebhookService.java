package com.arbitra.backend.service;

import com.arbitra.backend.dto.RazorpayWebhookPayload;
import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.model.Order;
import com.arbitra.backend.model.Transfer;
import com.arbitra.backend.repository.DisputeRepository;
import com.arbitra.backend.repository.OrderRepository;
import com.arbitra.backend.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;

@Service
public class WebhookService {

    @Autowired
    private DisputeRepository disputeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransferRepository transferRepository;

    public void processEvent(RazorpayWebhookPayload payload) {
        String eventType = payload.getEvent();
        System.out.println("Processing Razorpay event type: " + eventType);

        if ("dispute.created".equals(eventType) || "dispute.updated".equals(eventType)) {
            handleDisputeEvent(payload);
        } else {
            System.out.println("Unhandled event type received: " + eventType);
        }
    }

    private void handleDisputeEvent(RazorpayWebhookPayload payload) {
        try {
            Map<String, Object> payloadMap = payload.getPayload();
            if (payloadMap == null || !payloadMap.containsKey("dispute")) {
                System.out.println("Payload missing 'dispute' entity object.");
                return;
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> disputeEntity = (Map<String, Object>) ((Map<String, Object>) payloadMap.get("dispute")).get("entity");
            
            if (disputeEntity != null) {
                String gatewayDisputeId = (String) disputeEntity.get("id");
                String status = (String) disputeEntity.get("status");

                System.out.println("Parsing dispute -> Gateway ID: " + gatewayDisputeId + ", Status: " + status);

                // 1. Ensure a valid Order exists for foreign key constraint consistency
                Order order = orderRepository.findAll().stream().findFirst().orElseGet(() -> {
                    Order newOrder = new Order();
                    // Set required fields based on your Order model schema if needed
                    return orderRepository.save(newOrder);
                });

                // 2. Ensure a valid Transfer exists for foreign key constraint consistency
                Transfer transfer = transferRepository.findAll().stream().findFirst().orElseGet(() -> {
                    Transfer newTransfer = new Transfer();
                    // Set required fields based on your Transfer model schema if needed
                    return transferRepository.save(newTransfer);
                });

                // 3. Persist the Dispute securely linked to valid parent records
                Dispute dispute = new Dispute();
                dispute.setTitle("Dispute: " + gatewayDisputeId);
                dispute.setStatus(status != null ? status : "open");
                dispute.setOrder(order);
                dispute.setTransfer(transfer);
                dispute.setCreatedAt(OffsetDateTime.now());
                dispute.setUpdatedAt(OffsetDateTime.now());

                Dispute savedDispute = disputeRepository.save(dispute);
                System.out.println("Successfully persisted dispute state to database with ID: " + savedDispute.getId());
            }
        } catch (Exception e) {
            System.err.println("Error persisting dispute webhook payload: " + e.getMessage());
            e.printStackTrace();
        }
    }
}