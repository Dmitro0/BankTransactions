package com.myproject.bankwithkafka.mapper;

import com.myproject.bankwithkafka.dto.AccountCreationRequestDto;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapperToBankAccount {
    @Mapping(target = "number", source = "number")
    @Mapping(target = "balance", source = "request.initialBalance")
    @Mapping(target = "currency", source = "request.currency")
    @Mapping(target = "customerId", source = "request.customerId")
    BankAccount mapToBankAccount(AccountCreationRequestDto request, String number);
}
