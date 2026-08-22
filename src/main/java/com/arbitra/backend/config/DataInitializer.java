package com.arbitra.backend.config;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.model.Merchant;
import com.arbitra.backend.model.Order;
import com.arbitra.backend.repository.DisputeRepository;
import com.arbitra.backend.repository.MerchantRepository;
import com.arbitra.backend.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadSampleData(MerchantRepository merchantRepository,
                                            OrderRepository orderRepository,
                                            DisputeRepository disputeRepository) {
        return args -> {
            if (merchantRepository.count() == 0) {
                Merchant merchant = new Merchant();
                // If your entity uses different setters, let's save basic fields
                merchantRepository.save(merchant);

                Order order = new Order();
                orderRepository.save(order);

                Dispute dispute = new Dispute();
                dispute.setCreatedAt(OffsetDateTime.now());
                disputeRepository.save(dispute);

                System.out.println(">> Sample seed data successfully injected into PostgreSQL!");
            }
        };
    }
}