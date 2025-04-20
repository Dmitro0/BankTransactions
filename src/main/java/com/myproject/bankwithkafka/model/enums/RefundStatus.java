package com.myproject.bankwithkafka.model.enums;

import lombok.Getter;

@Getter
public enum RefundStatus {
    COMPLETED,
    FAILED;

    public static RefundStatus fromString(String string){
        for (RefundStatus refundStatus: RefundStatus.values()){
            if (refundStatus.toString().equalsIgnoreCase(string)){
                return refundStatus;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid RefundStatus: %s", string));
    }
}
