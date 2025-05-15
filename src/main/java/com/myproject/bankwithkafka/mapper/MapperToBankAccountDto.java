package com.myproject.bankwithkafka.mapper;

import com.myproject.bankwithkafka.dto.AccountResponseDto;
import com.myproject.bankwithkafka.model.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperToBankAccountDto {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    AccountResponseDto mapToBankAccountDto(BankAccount bankAccount);
}
