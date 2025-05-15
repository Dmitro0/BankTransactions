package com.myproject.bankwithkafka.dto;

import java.math.BigDecimal;

public record AccountCreationRequestDto(Long customerId, BigDecimal initialBalance, String currency) {
}
