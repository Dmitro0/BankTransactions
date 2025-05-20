package com.myproject.bankwithkafka.model.entity;

import com.myproject.bankwithkafka.model.enums.RefundStatus;
import com.myproject.bankwithkafka.model.enums.converter.RefundStatusConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refund")
public class Refund extends BaseEntity{

    private BigDecimal refundedAmount;
    private String reason;

    @Convert(converter = RefundStatusConverter.class)
    private RefundStatus refundStatus;

    @ManyToOne
    @JoinColumn(name = "paymentTransactionId", referencedColumnName = "id")
    private PaymentTransaction paymentTransaction;
}
