package com.myproject.bankwithkafka.dto;

import java.math.BigDecimal;

public record PaymentTransactionRequestDto(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount, String currency) {
}
