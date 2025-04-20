package com.myproject.bankwithkafka.model.entity;

import com.myproject.bankwithkafka.model.enums.RefundStatus;
import com.myproject.bankwithkafka.model.enums.converter.RefundStatusConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseEntity{

    private BigDecimal refundedAmount;
    private String reason;

    @Convert(converter = RefundStatusConverter.class)
    private RefundStatus refundStatus;

    @ManyToOne
    @JoinColumn(name = "paymentTransactionId", referencedColumnName = "id")
    private PaymentTransaction paymentTransaction;

    @OneToMany(mappedBy = "paymentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Refund> refunds;
}
