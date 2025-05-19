package com.myproject.bankwithkafka.dto;

import com.myproject.bankwithkafka.model.enums.TransactionStatus;

import java.math.BigDecimal;

public record PaymentTransactionRequestDto(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount, String currency) {
}
