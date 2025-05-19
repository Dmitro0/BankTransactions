package com.myproject.bankwithkafka.repositroy;

import com.myproject.bankwithkafka.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findBankAccountByNumber(String number);
}
