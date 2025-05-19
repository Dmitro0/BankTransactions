package com.myproject.bankwithkafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import com.myproject.bankwithkafka.model.entity.PaymentTransaction;

@Mapper(componentModel = "spring")
public interface MapperToPaymentTransaction {
    @Mapping(target = "amount", source = "request.amount")
    @Mapping(target = "currency", source = "request.currency")
    @Mapping(target = "transactionStatus", expression = "java(com.myproject.bankwithkafka.model.enums.TransactionStatus.PROCESSING)")
    @Mapping(target = "sourceBankAccount", source = "source")
    @Mapping(target = "destinationBankAccount", source = "destination")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    @Mapping(target = "refunds", ignore = true)
    PaymentTransaction mapToPaymentTransaction(PaymentTransactionRequestDto request, BankAccount source, BankAccount destination);
}
