package com.myproject.bankwithkafka.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDto(Long id, String number, BigDecimal balance, Long customerId, String currency, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
