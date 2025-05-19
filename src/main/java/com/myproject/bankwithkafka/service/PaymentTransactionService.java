package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.dto.PaymentTransactionResponseDto;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import com.myproject.bankwithkafka.repositroy.BankAccountRepository;
import com.myproject.bankwithkafka.repositroy.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentTransactionService {
    private final BankAccountRepository bankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransactionResponseDto initiateTransaction(PaymentTransactionRequestDto request){
        BankAccount sourceAccount = getAccountByNumber(request.sourceAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", request.sourceAccountNumber())));
        BankAccount destinationAccount = getAccountByNumber(request.destinationAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", request.destinationAccountNumber())));
        if (!checkCurrencyBetweenAccounts(sourceAccount, destinationAccount)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Currencies are not similar");
        }

        return null;
    }

    private Optional<BankAccount> getAccountByNumber(String number){
        return bankAccountRepository.findBankAccountByNumber(number);
    }

    private boolean checkCurrencyBetweenAccounts(BankAccount sourceAccount, BankAccount destinationAccount){
        return sourceAccount.getCurrency().equals(destinationAccount.getCurrency());
    }
}
