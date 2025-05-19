package com.myproject.bankwithkafka.controller.rest;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.dto.PaymentTransactionResponseDto;
import com.myproject.bankwithkafka.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionService paymentTransactionService;

    @PostMapping
    public ResponseEntity<PaymentTransactionResponseDto> processPayment(@RequestBody PaymentTransactionRequestDto request) {
        return ResponseEntity.ok(paymentTransactionService.initiateTransaction(request));
    }
}
