package com.arbitra.backend.repository;

import com.arbitra.backend.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findByRazorpayTransferId(String razorpayTransferId);
}