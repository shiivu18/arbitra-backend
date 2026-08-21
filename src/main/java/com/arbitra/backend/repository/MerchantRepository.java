package com.arbitra.backend.repository;

import com.arbitra.backend.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByRazorpayAccountId(String razorpayAccountId);
}