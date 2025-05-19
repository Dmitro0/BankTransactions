package com.myproject.bankwithkafka.repositroy;

import com.myproject.bankwithkafka.model.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<Long, PaymentTransaction> {
}
