package com.myproject.bankwithkafka.dto;

public record ErrorResponse(int status, String error, String message) {
}
