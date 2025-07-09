package org.example.commonservice.enums;

import lombok.Getter;

@Getter
public enum BookPaidConstant {

    PAID("Paid"), UNPAID("Unpaid");
    private final String message;

    BookPaidConstant(String message) {
        this.message = message;
    }
}
