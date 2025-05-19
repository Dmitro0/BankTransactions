package com.myproject.bankwithkafka.mapper;

import com.myproject.bankwithkafka.dto.PaymentTransactionResponseDto;
import com.myproject.bankwithkafka.model.entity.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperToPaymentResponse {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sourceAccountNumber", source = "sourceBankAccount.number")
    @Mapping(target = "destinationAccountNumber", source = "destinationBankAccount.number")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "transactionStatus", source = "transactionStatus")
    @Mapping(target = "createdAt", source = "createdAt")
    PaymentTransactionResponseDto mapToPaymentResponseDto(PaymentTransaction paymentTransaction);
}
