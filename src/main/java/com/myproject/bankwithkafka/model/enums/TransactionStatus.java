package com.myproject.bankwithkafka.model.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PROCESSING,
    SUCCESS,
    FAILED;

    public static TransactionStatus fromString(String string){
        for(TransactionStatus transactionStatus : TransactionStatus.values()){
            if (transactionStatus.toString().equalsIgnoreCase(string)){
                return transactionStatus;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid TransactionStatus: %s", string));
    }
}
