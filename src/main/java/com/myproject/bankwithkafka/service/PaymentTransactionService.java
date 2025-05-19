package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.dto.PaymentTransactionResponseDto;
import com.myproject.bankwithkafka.mapper.MapperToPaymentResponse;
import com.myproject.bankwithkafka.mapper.MapperToPaymentTransaction;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import com.myproject.bankwithkafka.model.entity.PaymentTransaction;
import com.myproject.bankwithkafka.model.enums.TransactionStatus;
import com.myproject.bankwithkafka.repositroy.BankAccountRepository;
import com.myproject.bankwithkafka.repositroy.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentTransactionService {
    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final MapperToPaymentTransaction mapperToPaymentTransaction;
    private final MapperToPaymentResponse mapperToPaymentResponse;

    @Transactional
    public PaymentTransactionResponseDto initiateTransaction(PaymentTransactionRequestDto request){
        BankAccount sourceAccount = getAccountByNumber(request.sourceAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", request.sourceAccountNumber())));
        BankAccount destinationAccount = getAccountByNumber(request.destinationAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", request.destinationAccountNumber())));
        if (!checkCurrencyBetweenAccounts(sourceAccount, destinationAccount)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Currencies are not similar");
        }
        PaymentTransaction paymentTransaction = mapperToPaymentTransaction.mapToPaymentTransaction(request, sourceAccount, destinationAccount);
        paymentTransactionRepository.save(paymentTransaction);
        bankAccountService.debitAccount(destinationAccount.getId(), request.amount());
        bankAccountService.creditAccount(sourceAccount.getId(), request.amount());
        paymentTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        paymentTransactionRepository.save(paymentTransaction);
        return mapperToPaymentResponse.mapToPaymentResponseDto(paymentTransaction);
    }

    private Optional<BankAccount> getAccountByNumber(String number){
        return bankAccountRepository.findBankAccountByNumber(number);
    }

    private boolean checkCurrencyBetweenAccounts(BankAccount sourceAccount, BankAccount destinationAccount){
        return sourceAccount.getCurrency().equals(destinationAccount.getCurrency());
    }
}
