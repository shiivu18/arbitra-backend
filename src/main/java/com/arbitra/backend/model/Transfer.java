package com.arbitra.backend.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "razorpay_transfer_id", nullable = false, unique = true)
    private String razorpayTransferId;

    @Column(name = "amount_paise", nullable = false)
    private Long amountPaise;

    @Column(name = "on_hold", nullable = false)
    private Boolean onHold = true;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }
    public String getRazorpayTransferId() { return razorpayTransferId; }
    public void setRazorpayTransferId(String razorpayTransferId) { this.razorpayTransferId = razorpayTransferId; }
    public Long getAmountPaise() { return amountPaise; }
    public void setAmountPaise(Long amountPaise) { this.amountPaise = amountPaise; }
    public Boolean getOnHold() { return onHold; }
    public void setOnHold(Boolean onHold) { this.onHold = onHold; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}