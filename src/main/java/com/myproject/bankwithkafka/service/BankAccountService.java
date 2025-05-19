package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.repositroy.BankAccountRepository;
import com.myproject.bankwithkafka.dto.AccountCreationRequestDto;
import com.myproject.bankwithkafka.dto.AccountResponseDto;
import com.myproject.bankwithkafka.mapper.MapperToBankAccount;
import com.myproject.bankwithkafka.mapper.MapperToBankAccountDto;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BankAccountService {
    private final MapperToBankAccountDto mapperToBankAccountDto;
    private final MapperToBankAccount mapperToBankAccount;
    private final BankAccountRepository bankAccountRepository;

    public AccountResponseDto getAccountByNumber(String number){
        BankAccount bankAccount = bankAccountRepository.findBankAccountByNumber(number)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id: %s doesn't exists", number)));
        return mapperToBankAccountDto.mapToBankAccountDto(bankAccount);
    }

    @Transactional
    public AccountResponseDto createAccount(AccountCreationRequestDto request){
        String accountNumber = UUID.randomUUID().toString();
        Optional<BankAccount> existingAccount = bankAccountRepository.findBankAccountByNumber(accountNumber);
        if (existingAccount.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Number %s already exists", accountNumber));
        }
        BankAccount bankAccount = mapperToBankAccount.mapToBankAccount(request, accountNumber);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return mapperToBankAccountDto.mapToBankAccountDto(savedAccount);
    }

    @Transactional
    public void debitAccount(Long accountId, BigDecimal amount){
        BankAccount bankAccount = getAccountById(accountId);
        bankAccount.setBalance(bankAccount.getBalance().add(amount));
    }

    @Transactional
    public void creditAccount(Long accountId, BigDecimal amount){
        BankAccount bankAccount = getAccountById(accountId);
        if (!checkBalance(bankAccount.getBalance(), amount)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Amount larger then balance!");
        }
        bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
    }

    private boolean checkBalance(BigDecimal balance, BigDecimal amount){
        return balance.compareTo(amount) > 0;
    }

    private BankAccount getAccountById(Long accountId){
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id: %d doesn't exists", accountId)));
    }
}
