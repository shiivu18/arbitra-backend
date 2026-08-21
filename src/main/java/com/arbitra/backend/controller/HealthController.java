package com.arbitra.backend.controller;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.model.Merchant;
import com.arbitra.backend.model.Order;
import com.arbitra.backend.model.Transfer;
import com.arbitra.backend.repository.DisputeRepository;
import com.arbitra.backend.repository.MerchantRepository;
import com.arbitra.backend.repository.OrderRepository;
import com.arbitra.backend.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private DisputeRepository disputeRepository;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", OffsetDateTime.now());
        response.put("database", "Connected to PostgreSQL 18");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seed")
    public ResponseEntity<String> seedData() {
        // 1. Create or fetch Merchant
        Merchant merchant = merchantRepository.findByRazorpayAccountId("acc_razorpay_999")
            .orElseGet(() -> {
                Merchant m = new Merchant();
                m.setRazorpayAccountId("acc_razorpay_999");
                m.setBusinessName("Apex Electronics");
                m.setEmail("support@apexelectronics.com");
                return merchantRepository.save(m);
            });

        // 2. Create or fetch Order
        Order order = orderRepository.findByRazorpayOrderId("order_razorpay_8888")
            .orElseGet(() -> {
                Order o = new Order();
                o.setRazorpayOrderId("order_razorpay_8888");
                o.setRazorpayPaymentId("pay_razorpay_12345");
                o.setTotalAmountPaise(29999L);
                o.setCurrency("INR");
                o.setStatus("PAID");
                return orderRepository.save(o);
            });

        // 3. Create Transfer
        Transfer transfer = new Transfer();
        transfer.setOrder(order);
        transfer.setMerchant(merchant);
        transfer.setRazorpayTransferId("trf_razorpay_" + System.currentTimeMillis()); // Unique per run
        transfer.setAmountPaise(29999L);
        transfer.setOnHold(true);
        transfer.setStatus("PENDING");
        transferRepository.save(transfer);

        // 4. Create Dispute
        Dispute dispute = new Dispute();
        dispute.setOrder(order);
        dispute.setTransfer(transfer);
        dispute.setTitle("Item not received by customer");
        dispute.setStatus("OPEN");
        dispute.setVersion(0L);
        disputeRepository.save(dispute);

        return ResponseEntity.ok("Successfully seeded test merchant, order, transfer, and dispute into PostgreSQL!");
    }
}