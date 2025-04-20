package com.myproject.bankwithkafka.model.entity;

import com.myproject.bankwithkafka.model.enums.TransactionStatus;
import com.myproject.bankwithkafka.model.enums.converter.TransactionStatusConverter;
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
public class PaymentTransaction extends BaseEntity{
    private BigDecimal amount;
    private String currency;

    @Convert(converter = TransactionStatusConverter.class)
    private TransactionStatus transactionStatus;

    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "sourceBankAccountId")
    private BankAccount sourceBankAccount;

    @ManyToOne
    @JoinColumn(name = "destinationBankAccountId")
    private BankAccount destinationBankAccount;

    @OneToMany
    private List<Refund> refunds;
}
