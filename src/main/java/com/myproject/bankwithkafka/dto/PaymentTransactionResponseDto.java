package com.myproject.bankwithkafka.dto;

import com.myproject.bankwithkafka.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentTransactionResponseDto(Long id,
                                            String sourceAccountNumber,
                                            String destinationAccountNumber,
                                            BigDecimal amount,
                                            String currency,
                                            TransactionStatus transactionStatus,
                                            LocalDateTime createdAt) {}
