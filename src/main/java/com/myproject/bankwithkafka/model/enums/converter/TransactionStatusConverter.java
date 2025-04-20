package com.myproject.bankwithkafka.model.enums.converter;

import com.myproject.bankwithkafka.model.enums.TransactionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, String> {
    @Override
    public String convertToDatabaseColumn(TransactionStatus transactionStatus) {
        return transactionStatus == null ? null : transactionStatus.name();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TransactionStatus.fromString(dbData);
    }
}
