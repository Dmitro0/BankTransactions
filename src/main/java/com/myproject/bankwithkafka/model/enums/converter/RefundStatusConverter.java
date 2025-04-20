package com.myproject.bankwithkafka.model.enums.converter;

import com.myproject.bankwithkafka.model.enums.RefundStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RefundStatusConverter implements AttributeConverter<RefundStatus, String> {
    @Override
    public String convertToDatabaseColumn(RefundStatus refundStatus) {
        return refundStatus == null ? null : refundStatus.name();
    }

    @Override
    public RefundStatus convertToEntityAttribute(String dbStatus) {
        return dbStatus == null ? null : RefundStatus.fromString(dbStatus);
    }
}
