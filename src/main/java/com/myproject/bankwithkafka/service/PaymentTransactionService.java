package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.dto.PaymentTransactionResponseDto;
import com.myproject.bankwithkafka.mapper.MapperToPaymentResponse;
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

@RequiredArgsConstructor
@Service
public class PaymentTransactionService {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final MapperToPaymentResponse mapperToPaymentResponse;

    @Transactional
    public PaymentTransactionResponseDto initiateTransaction(PaymentTransactionRequestDto request) {
        PaymentTransaction paymentTransaction = transactionService.createProcessingTransaction(request);
        try {
            return processTransaction(paymentTransaction, request);
        } catch (ResponseStatusException e){
            transactionService.updateTransactionStatus(paymentTransaction.getId(), TransactionStatus.FAILED, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction failed: " + e.getMessage(), e);
        }
    }
    @Transactional
    private PaymentTransactionResponseDto processTransaction(PaymentTransaction paymentTransaction, PaymentTransactionRequestDto request) {
        BankAccount sourceAccount = getAccountByNumber(request.sourceAccountNumber());
        BankAccount destinationAccount = getAccountByNumber(request.destinationAccountNumber());
        bankAccountService.debitAccount(destinationAccount.getId(), request.amount());
        bankAccountService.creditAccount(sourceAccount.getId(), request.amount());
        paymentTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        paymentTransactionRepository.save(paymentTransaction);
        return mapperToPaymentResponse.mapToPaymentResponseDto(paymentTransaction);
    }

    private BankAccount getAccountByNumber(String number) {
        return bankAccountRepository.findBankAccountByNumber(number).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", number)));
    }
}