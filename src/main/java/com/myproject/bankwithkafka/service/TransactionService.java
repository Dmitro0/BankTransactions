package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.mapper.MapperToPaymentTransaction;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import com.myproject.bankwithkafka.model.entity.PaymentTransaction;
import com.myproject.bankwithkafka.model.enums.TransactionStatus;
import com.myproject.bankwithkafka.repositroy.BankAccountRepository;
import com.myproject.bankwithkafka.repositroy.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final MapperToPaymentTransaction mapperToPaymentTransaction;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaymentTransaction createProcessingTransaction(PaymentTransactionRequestDto request){
        BankAccount sourceAccount = getAccountByNumber(request.sourceAccountNumber());
        BankAccount destinationAccount = getAccountByNumber(request.destinationAccountNumber());
        if (!checkCurrencyBetweenAccounts(sourceAccount, destinationAccount)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Currencies are not similar");
        }
        PaymentTransaction paymentTransaction = mapperToPaymentTransaction.mapToPaymentTransaction(request, sourceAccount, destinationAccount);
        paymentTransaction.setTransactionStatus(TransactionStatus.PROCESSING);
        return paymentTransactionRepository.save(paymentTransaction);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTransactionStatus(Long transactionId, TransactionStatus status, String errorMessage) {
        PaymentTransaction transaction = paymentTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        transaction.setTransactionStatus(status);
        transaction.setErrorMessage(errorMessage);
        paymentTransactionRepository.save(transaction);
    }

    private boolean checkCurrencyBetweenAccounts(BankAccount sourceAccount, BankAccount destinationAccount) {
        return sourceAccount.getCurrency().equals(destinationAccount.getCurrency());
    }
    private BankAccount getAccountByNumber(String number) {
        return bankAccountRepository.findBankAccountByNumber(number).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with number: %s doesn't exists", number)));
    }
}
