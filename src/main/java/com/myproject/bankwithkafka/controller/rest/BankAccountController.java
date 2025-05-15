package com.myproject.bankwithkafka.controller.rest;

import com.myproject.bankwithkafka.dto.AccountCreationRequestDto;
import com.myproject.bankwithkafka.dto.AccountResponseDto;
import com.myproject.bankwithkafka.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> getAccountByNumber(@PathVariable("accountNumber") String number){
        return ResponseEntity.ok(bankAccountService.getAccountByNumber(number));
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountCreationRequestDto request){
        return ResponseEntity.ok(bankAccountService.createAccount(request));
    }

}
