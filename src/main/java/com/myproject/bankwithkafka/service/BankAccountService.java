package com.myproject.bankwithkafka.service;

import com.myproject.bankwithkafka.BankAccountRepository;
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
}
